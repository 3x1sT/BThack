package com.bt.BThack.impl.Commands;

import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import org.apache.commons.lang3.math.NumberUtils;

public class RotateCommand extends AbstractCommand {

    public RotateCommand() {
        super("Rotates the player's camera by specified numbers", "rotate [yaw{-30000 : 30000}] [pitch{-90 : 90}]", "r" + "otate"
        );
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 2 && NumberUtils.isNumber(args[0]) && NumberUtils.isNumber(args[1])) {
            int yaw = Integer.parseInt(args[0]);
            int pitch = Integer.parseInt(args[1]);

            if (isCorrectRotations(yaw, pitch)) {

                mc.player.rotationYaw = yaw;
                mc.player.rotationPitch = pitch;
            } else {
                invalidArgumentError();
            }
        } else {
            invalidArgumentError();
        }
    }

    private boolean isCorrectRotations(int yaw, int pitch) {
        return yaw >= -30000 && yaw <= 30000 && pitch >= -90 && pitch <= 90;
    }
}
