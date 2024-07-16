package com.bt.BThack.System;

import com.bt.BThack.BThack;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Allies.AlliesUtils;
import com.bt.BThack.api.Social.Allies.Ally;
import com.bt.BThack.impl.CustomGui.Gui.Client.ClickGui.ClickGuiManager;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.Settings.Setting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.Frame;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotConfig;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks.*;
import com.google.gson.*;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class ConfigInit {
    private static final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();

    public static void registerFiles(String name, String path) throws IOException {
        Path p = Paths.get("BThack/" + path + "/" + name + ".json");
        if (Files.exists(p)) {
            File file = new File("BThack/" + path + "/" + name + ".json");
            file.delete();
        }

        Files.createFile(p);
    }

    public static void saveConfig() {
        try {
            saveModules();
            saveClickGui();
            saveAllies();
            saveActionBotTasks();
        } catch (IOException e) {
            BThack.error(e.getMessage());
        }
    }

    public static void loadConfig() {
        try {
            loadModules();
            loadClickGui();
            loadAllies();
            loadActionBotTasks();
        } catch (IOException e) {
            BThack.error(e.getMessage());
        }
    }

    public static void saveModules() throws IOException {
        for (Module module : Client.modules) {
            registerFiles(module.getName(), "Modules");
            OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("BThack/Modules/" + module.getName() + ".json")), StandardCharsets.UTF_8);

            JsonObject moduleObject = new JsonObject();
            JsonObject settingObject = new JsonObject();

            moduleObject.add("Name", new JsonPrimitive(module.getName()));
            moduleObject.add("Enabled", new JsonPrimitive(module.isEnabled()));
            moduleObject.add("Bind", new JsonPrimitive(module.getKey()));
            moduleObject.add("Visible", new JsonPrimitive(module.visible));

            if (BThack.instance.settingsManager.getSettingsByMod(module) != null) {
                for (Setting s : BThack.instance.settingsManager.getSettingsByMod(module)) {
                    if (s.isCombo()) {
                        settingObject.add(s.getName(), new JsonPrimitive(s.getValString()));
                        settingObject.add(s.getName() + " Index", new JsonPrimitive(s.getIndex()));
                    }

                    if (s.isCheck()) {
                        settingObject.add(s.getName(), new JsonPrimitive(s.getValBoolean()));
                    }

                    if (s.isSlider()) {
                        settingObject.add(s.getName(), new JsonPrimitive(s.getValDouble()));
                    }
                    if (s.isKeyCode()) {
                        settingObject.add(s.getName(), new JsonPrimitive(s.getKeyCodeVal()));
                    }
                }
            }

            moduleObject.add("Settings", settingObject);
            String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
            fileOutputStreamWriter.write(jsonString);
            fileOutputStreamWriter.close();
        }
    }

    public static void loadModules() throws IOException {
        for (Module module : Client.modules) {
            if (!Files.exists(Paths.get("BThack/Modules/" + module.getName() + ".json"))) {
                if (module.isAutoEnabled()) {
                    module.setToggled(true);
                }
                continue;
            }

            InputStream inputStream = Files.newInputStream(Paths.get("BThack/Modules/" + module.getName() + ".json"));
            JsonObject moduleObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

            if (moduleObject.get("Name") == null || moduleObject.get("Enabled") == null || moduleObject.get("Bind") == null || moduleObject.get("Visible") == null) continue;

            JsonObject settingObject = moduleObject.get("Settings").getAsJsonObject();

            if (BThack.instance.settingsManager.getSettingsByMod(module) != null) {
                for (Setting s : BThack.instance.settingsManager.getSettingsByMod(module)) {
                    JsonElement settingValueObject;
                    JsonElement IndexObject;

                    settingValueObject = settingObject.get(s.getName());

                    if (settingValueObject != null) {
                        if (s.isCombo()) {
                            IndexObject = settingObject.get(s.getName() + " Index");
                            s.setValString(settingValueObject.getAsString());
                            s.setIndex(IndexObject.getAsInt());
                        }

                        if (s.isCheck()) {
                            s.setValBoolean(settingValueObject.getAsBoolean());
                        }

                        if (s.isSlider()) {
                            s.setValDouble(settingValueObject.getAsDouble());
                        }

                        if (s.isKeyCode()) {
                            s.setKeyCodeVal(settingValueObject.getAsInt());
                        }
                    }
                }
            }
            module.setToggled(moduleObject.get("Enabled").getAsBoolean());
            module.setKey(moduleObject.get("Bind").getAsInt());
            module.visible = moduleObject.get("Visible").getAsBoolean();
        }
    }

    public static void saveClickGui() throws IOException {
        registerFiles("ClickGui", "ClickGui");

        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("BThack/ClickGui/ClickGui.json")), StandardCharsets.UTF_8);

        JsonObject clickGuiObject = new JsonObject();

        for (Frame frame : ClickGuiManager.frames) {
            JsonObject frameObject = new JsonObject();

            frameObject.add("x", new JsonPrimitive(frame.getX()));
            frameObject.add("y", new JsonPrimitive(frame.getY()));
            frameObject.add("opened", new JsonPrimitive(frame.isOpen()));

            clickGuiObject.add(frame.getCategory().name(), frameObject);
        }

        String jsonString = gson.toJson(new JsonParser().parse(clickGuiObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }

    public static void loadClickGui() throws IOException {
        Path path = Paths.get("BThack/ClickGui/ClickGui.json");
        if (Files.exists(path)) {
            InputStream inputStream = Files.newInputStream(path);
            JsonObject clickGuiObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();
            for (Frame frame : ClickGuiManager.frames) {
                JsonObject settingObject = clickGuiObject.get(frame.getCategory().name()).getAsJsonObject();

                frame.setX(settingObject.get("x").getAsInt());
                frame.setY(settingObject.get("y").getAsInt());
                frame.setOpen(settingObject.get("opened").getAsBoolean());
            }
        }
    }

    public static void saveAllies() throws IOException {
        for (Ally ally : AlliesUtils.allies) {
            registerFiles(ally.getName(), "Social/Allies");

            OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("BThack/Social/Allies/" + ally.getName() + ".json")), StandardCharsets.UTF_8);

            JsonObject alliesObject = new JsonObject();

            alliesObject.add("name", new JsonPrimitive(ally.getName()));
            alliesObject.add("clanName", new JsonPrimitive(ally.getClanName()));
            alliesObject.add("R", new JsonPrimitive(ally.getR()));
            alliesObject.add("G", new JsonPrimitive(ally.getG()));
            alliesObject.add("B", new JsonPrimitive(ally.getB()));

            String jsonString = gson.toJson(new JsonParser().parse(alliesObject.toString()));
            fileOutputStreamWriter.write(jsonString);
            fileOutputStreamWriter.close();
        }
    }

    public static void loadAllies() throws IOException {
        AlliesUtils.allies.clear();
        File folder = new File(Paths.get("BThack/Social/Allies").toUri());
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (Objects.equals(FilenameUtils.getExtension(file.getName()), "json")) {
                        String name = file.getName();
                        InputStream inputStream = Files.newInputStream(Paths.get("BThack/Social/Allies/" + name));
                        JsonObject allyObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

                        if (allyObject.get("name") != null && allyObject.get("clanName") != null && allyObject.get("R") != null && allyObject.get("G") != null && allyObject.get("B") != null) {
                            AlliesUtils.allies.add(new Ally(allyObject.get("name").getAsString(), allyObject.get("clanName").getAsString(), allyObject.get("R").getAsFloat(), allyObject.get("G").getAsFloat(), allyObject.get("B").getAsFloat()));
                        }
                    }
                }
            }
        }
    }

    public static void saveActionBotTasks() throws IOException {
        Path configInfoFile = Paths.get("BThack/ActionBot/ConfigInfo.txt");

        BufferedWriter writer = Files.newBufferedWriter(configInfoFile, StandardCharsets.UTF_8);
        ArrayList<ActionBotTask> tasks = ActionBotConfig.tasks;
        tasks.remove(ActionBotConfig.startTask);
        tasks.remove(ActionBotConfig.endTask);

        int taskNumber = 1;

        boolean m = false;

        for (ActionBotTask task : tasks) {
            String fileName = taskNumber + ". " + task.getName();

            registerFiles(fileName, "ActionBot/DefaultConfig");

            OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("BThack/ActionBot/DefaultConfig/" + fileName + ".json")), StandardCharsets.UTF_8);

            JsonObject taskObject = new JsonObject();

            if (task.isMoveTask()) {
                MoveTask moveTask = (MoveTask) task;

                String type;

                switch (moveTask.getType()) {
                    case Default:
                        type = "Default";
                        break;
                    case AutoJump:
                        type = "With AutoJump";
                        break;
                    case Through_Obstacles:
                    default:
                        type = "Through Obstacles";
                        break;
                }

                taskObject.add("Mode", new JsonPrimitive(moveTask.mode));

                taskObject.add("NeedX", new JsonPrimitive(moveTask.getNeedX()));
                taskObject.add("NeedZ", new JsonPrimitive(moveTask.getNeedZ()));
                taskObject.add("Scaffold", new JsonPrimitive(moveTask.isScaffold()));
                taskObject.add("Type", new JsonPrimitive(type));
            } else
            if (task.isSendMessageTask()) {
                SendMessageTask sendMessageTask = (SendMessageTask) task;

                taskObject.add("Mode", new JsonPrimitive(sendMessageTask.mode));

                taskObject.add("Message", new JsonPrimitive(sendMessageTask.getMessage()));
            } else
            if (task.isJumpTask()) {
                JumpTask jumpTask = (JumpTask) task;

                taskObject.add("Mode", new JsonPrimitive(jumpTask.mode));
            } else
            if (task.isWaitTask()) {
                WaitTask waitTask = (WaitTask) task;

                taskObject.add("Mode", new JsonPrimitive(waitTask.mode));

                taskObject.add("Seconds", new JsonPrimitive(waitTask.getSeconds()));
            } else
            if (task.isTunnelTask()) {
                TunnelTask tunnelTask = (TunnelTask) task;

                String direction;
                switch (tunnelTask.getDirection()) {
                    case X_PLUS:
                        direction = "X+";
                        break;
                    case X_MINUS:
                        direction = "X-";
                        break;
                    case Z_PLUS:
                        direction = "Z+";
                        break;
                    case Z_MINUS:
                    default:
                        direction = "Z-";
                        break;
                }

                taskObject.add("Mode", new JsonPrimitive(tunnelTask.mode));

                taskObject.add("Direction", new JsonPrimitive(direction));
                taskObject.add("Length", new JsonPrimitive(tunnelTask.getLength()));
            }



            if (!m) {
                writer.write(fileName);
                m = true;
            } else {
                writer.write(System.lineSeparator() + fileName);
            }

            taskNumber++;

            String jsonString = gson.toJson(new JsonParser().parse(taskObject.toString()));
            fileOutputStreamWriter.write(jsonString);
            fileOutputStreamWriter.close();
        }

        writer.close();
    }

    public static void loadActionBotTasks() throws IOException {
        Path configInfoFile = Paths.get("BThack/ActionBot/ConfigInfo.txt");

        if (Files.exists(configInfoFile)) {
            BufferedReader readerConfigInfo = Files.newBufferedReader(configInfoFile, StandardCharsets.UTF_8);
            String line = readerConfigInfo.readLine();

            ArrayList<String> tasksNames = new ArrayList<>();

            ArrayList<ActionBotTask> tasks = new ArrayList<>();

            while (line != null) {
                tasksNames.add(line);
                line = readerConfigInfo.readLine();
            }

            readerConfigInfo.close();

            for (String taskName : tasksNames) {
                Path taskPath = Paths.get("BThack/ActionBot/DefaultConfig/" + taskName + ".json");

                if (Files.exists(taskPath)) {
                    InputStream inputStream = Files.newInputStream(taskPath);

                    JsonObject taskObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

                    if (taskObject.get("Mode") != null) {
                        String mode = taskObject.get("Mode").getAsString();

                        if (ActionBotTask.isJumpTask(mode)) {
                            tasks.add(new JumpTask());
                        }
                        if (ActionBotTask.isMoveTask(mode)) {
                            if (taskObject.get("NeedX") != null && taskObject.get("NeedZ") != null && taskObject.get("Scaffold") != null && taskObject.get("Type") != null) {
                                double needX = taskObject.get("NeedX").getAsDouble();
                                double needZ = taskObject.get("NeedZ").getAsDouble();
                                boolean scaffold = taskObject.get("Scaffold").getAsBoolean();
                                String type = taskObject.get("Type").getAsString();

                                MoveTask.Type moveType = type.equals("Default") ? MoveTask.Type.Default : type.equals("With AutoJump") ? MoveTask.Type.AutoJump : MoveTask.Type.Through_Obstacles;

                                tasks.add(new MoveTask(needX, needZ, scaffold, moveType));
                            }
                        }
                        if (ActionBotTask.isTunnelTask(mode)) {
                            if (taskObject.get("Direction") != null && taskObject.get("Length") != null) {
                                String directionString = taskObject.get("Direction").getAsString();
                                double length = taskObject.get("Length").getAsDouble();

                                TunnelTask.Direction direction;


                                switch (directionString) {
                                    case "X+":
                                        direction = TunnelTask.Direction.X_PLUS;
                                        break;
                                    case "X-":
                                        direction = TunnelTask.Direction.X_MINUS;
                                        break;
                                    case "Z+":
                                        direction = TunnelTask.Direction.Z_PLUS;
                                        break;
                                    case "Z-":
                                    default:
                                        direction = TunnelTask.Direction.Z_MINUS;
                                        break;
                                }

                                tasks.add(new TunnelTask(direction, length));
                            }
                        }
                        if (ActionBotTask.isSendMessageTask(mode)) {
                            if (taskObject.get("Message") != null) {
                                String message = taskObject.get("Message").getAsString();

                                tasks.add(new SendMessageTask(message));
                            }
                        }
                        if (ActionBotTask.isWaitTask(mode)) {
                            if (taskObject.get("Seconds") != null) {
                                double seconds = taskObject.get("Seconds").getAsDouble();

                                tasks.add(new WaitTask(seconds));
                            }
                        }
                    }
                }
            }

            ActionBotConfig.tasks.remove(ActionBotConfig.endTask);

            ActionBotConfig.tasks.addAll(tasks);

            ActionBotConfig.tasks.add(ActionBotConfig.endTask);
        }
    }


    public static void loadColourThemes() throws IOException {
        Client.addCTheme("Default", 0x191CFF, 0xFF111111, 0xFF222222, 0x191CFF, 0xFFFFFF, 0x191CFF);
        Client.addCTheme("PurpleNight", 0xD902EE, 0x320D3E, 0x360E42, 0xF162FF, 0xFFD79D, 0xD902EE);
        Client.addCTheme("PinkAndBrown", 0xBE1558, 0x322514, 0x3A2B17, 0xE75874, 0xFBCBC9, 0xBE1558);
        Client.addCTheme("Chestnut", 0xDD8F2E, 0x2E1104, 0x341406, 0xDDB869, 0x956429, 0xDD8F2E);
        Client.addCTheme("ChocolateBrownie", 0xB6452C, 0x301B28, 0x381F2E, 0xDDC5A2, 0x523634, 0xB6452C);
        Client.addCTheme("CitrusMix", 0x902B04, 0x2B2E0E, 0x333611, 0xD9901C, 0xF7E9B3, 0xD9901C);
        Client.addCTheme("CopperMountain", 0x206F7C, 0x211B1A, 0x251F1D, 0x77341D, 0xB17365, 0x206F7C);
        Client.addCTheme("Crocuses", 0xDFBD99, 0x4C1905, 0x561D07, 0xBA4A1B, 0xC4A189, 0xDFBD99);
        Client.addCTheme("Freshness", 0x62A77C, 0x153626, 0x183D2B, 0x7CC398, 0xADCBB5, 0x62A77C);
        Client.addCTheme("Ocean", 0x5A89B9, 0x0A1C34, 0x0B1F3A, 0xC1D9F9, 0x2E4553, 0x5A89B9);
        Client.addCTheme("Spice", 0xA96946, 0x271007, 0x2F1308, 0xA96946, 0xECCDB1, 0xA96946);

        File folder = Paths.get("BThack/Themes/ColourThemes").toFile();
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (Objects.equals(FilenameUtils.getExtension(file.getName()), "json")) {
                        String name = file.getName();
                        InputStream inputStream = Files.newInputStream(Paths.get("BThack/Themes/ColourThemes/" + name));
                        JsonObject colourThemeObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

                        if (colourThemeObject.get("Name") != null) {
                            JsonObject colourObject = colourThemeObject.get("Colours").getAsJsonObject();

                            JsonElement fontColour;
                            JsonElement backgroundFontColour;
                            JsonElement backgroundFontHoveredColour;
                            JsonElement moduleEnabledColour;
                            JsonElement moduleDisabledColour;
                            JsonElement arrayListColour;

                            fontColour = colourObject.get("fontColour");
                            backgroundFontColour = colourObject.get("backgroundFontColour");
                            backgroundFontHoveredColour = colourObject.get("backgroundFontColour");
                            moduleEnabledColour = colourObject.get("moduleEnabledColour");
                            moduleDisabledColour = colourObject.get("moduleDisabledColour");
                            arrayListColour = colourObject.get("arrayListColour");

                            Client.addCTheme(colourThemeObject.get("Name").getAsString(), fontColour.getAsInt(), backgroundFontColour.getAsInt(), backgroundFontHoveredColour.getAsInt(), moduleEnabledColour.getAsInt(), moduleDisabledColour.getAsInt(), arrayListColour.getAsInt());
                        }
                    }
                }
            }
        }
    }
}
