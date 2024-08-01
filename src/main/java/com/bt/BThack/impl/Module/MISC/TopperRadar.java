package com.bt.BThack.impl.Module.MISC;

import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Clans.ClansUtils;
import com.bt.BThack.api.Social.Friends.FriendsUtils;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import com.bt.BThack.api.Utils.Modules.KillAuraUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopperRadar extends Module {
    protected static boolean pause = false;
    public TopperRadar() {
        super("TopperRadar",
                "Warns you if there is a topper nearby.",
                Keyboard.KEY_NONE,
                Category.MISC,
                false
        );

        addCheckbox("Only Diamond Armor", this, true);
        addSlider("Min match threshold", this, 3, 1, 4, true);
        addSlider("Min amount of armor", this, 3, 1, 4, true);

        addCheckbox("Friends", this, false);

        ClansUtils.addClanManagerInModule(this);

        addCheckbox("AutoDisconnect", this, false);
        addSlider("AutoDisconnect", true,"Shutdown Delay", this, 3, 1, 10, true);
    }

    /*
    ������: id:0 ���� ���:4
    ������������� id:1 ���� ���:4
    �����������: id:2 ���� ���:4
    ������������������: id:3 ���� ���:4
    ������ �� ��������: id:4 ���� ���:4
    ��������� �������: id:5 ���� ���:3
    ���������: id:6 ���� ���:1
    ����: id:7 ���� ���:3
    ��������� ������: id:8 ���� ���:3
    �������: id:9 ���� ���:2

    �������: id:16 ���� ���:5
    �������� ����: id:17 ���� ���:5
    ��� �������������: id:18 ���� ���:5
    ������: id:19 ���� ���:2
    ������� ����: id:20 ���� ���:2
    ������: id:21 ���� ���:3
    ������� ������: id:22 ���� ���:3

    �������������: id:32 ���� ���:5
    �������� �������: id:33 ���� ���:1
    ���������: id:34 ���� ���:3
    �����: id:35 ���� ���:3

    ����: id:48 ���� ���:5
    �����������: id:49 ���� ���:2
    �������� ������: id:50 ���� ���:1
    �������������: id:51 ���� ���:1

    ������� �����: id:61 ���� ���:3
    ��������: id:62 ���� ���:3

    �������: id:70 ���� ���:1

     */

    private static final ArrayList<EntityPlayer> reportedToppers = new ArrayList<>();

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck() || pause) return;

        arrayListInfo = getCheckbox(this.name, "AutoDisconnect") ? "AutoDisconnect" : "";

        for (EntityPlayer player : mc.world.playerEntities) {
            if (player == mc.player || reportedToppers.contains(player)) continue;
            if (!getCheckbox(this.name, "Friends")) {
                if (FriendsUtils.isFriend(player)) continue;
            }
            if (!KillAuraUtils.isSuccessfulClanMember(player, this.name)) continue;

            Iterable<ItemStack> equipmentArmor = player.getEquipmentAndArmor();

            byte armorMatches = 0;

            for (ItemStack itemStack : equipmentArmor) {
                byte enchantmentMatches = 0;
                if (itemStack.getItem() instanceof ItemArmor) {

                    ItemArmor armor = (ItemArmor) itemStack.getItem();
                    if (getCheckbox(this.name, "Only Diamond Armor")) {
                        if (armor.getArmorMaterial() != ItemArmor.ArmorMaterial.DIAMOND) continue;
                    }
                    for (NBTBase enchantment : itemStack.getEnchantmentTagList()) {
                        byte[] enchantmentInfo = {0,0};
                        Pattern pattern = Pattern.compile("\\d+");
                        Matcher matcher = pattern.matcher(enchantment.toString());
                        byte i = 0;
                        while (matcher.find()) {
                            byte b = Byte.parseByte(matcher.group());
                            enchantmentInfo[i] = b;
                            i++;
                        }
                        if (
                                enchantmentInfo[0] == 4 && enchantmentInfo[1] == 0 ||
                                        enchantmentInfo[0] == 4 && enchantmentInfo[1] == 3 ||
                                        enchantmentInfo[0] == 3 && enchantmentInfo[1] == 7 ||
                                        enchantmentInfo[0] == 1 && enchantmentInfo[1] == 70
                        ) {
                            enchantmentMatches++;
                        }
                    }
                }

                if (enchantmentMatches >= getSlider(this.name, "Min match threshold")) {
                    armorMatches++;
                }
            }

            if (armorMatches >= getSlider(this.name, "Min amount of armor")) {
                String pattern = "#0.0";
                String health = new DecimalFormat(pattern).format(player.getHealth());

                ChatUtils.sendMessage(ChatFormatting.GOLD + "The" + ChatFormatting.RED + " topper " + ChatFormatting.GOLD +"has been spotted! " + ChatFormatting.AQUA + "Name: " + ChatFormatting.WHITE + player.getDisplayNameString() + "  " + ChatFormatting.AQUA + "Health: " + ChatFormatting.WHITE + health + "  " + ChatFormatting.AQUA + "Direction: " + ChatFormatting.WHITE + AimBotUtils.getDirection(player), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP);

                reportedToppers.add(player);

                if (getCheckbox(this.name, "AutoDisconnect")) {
                    ThreadManager.startNewThread(thread -> {

                        int delay = (int) Module.getSlider("TopperRadar", "Shutdown Delay");

                        ChatUtils.sendMessage(ChatFormatting.RED + "The countdown to disconnecting begins!");

                        while (delay != 0) {
                            ChatUtils.sendMessage(ChatFormatting.RED + "Disconnect after " + delay + "s.");

                            try {
                                thread.sleep(1000);
                            } catch (InterruptedException ignored) {}

                            delay--;
                        }

                        mc.player.connection.handleDisconnect(new SPacketDisconnect());

                        pause = false;
                    });
                    pause = true;
                }
            }
        }

        reportedToppers.removeIf(player -> !mc.world.playerEntities.contains(player));
    }
}
