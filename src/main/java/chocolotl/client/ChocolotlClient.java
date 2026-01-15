package chocolotl.client;

import chocolotl.screens.TemperingMachineScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

import static chocolotl.ChocolotlRegistry.TEMPERING_MACHINE_SCREEN_HANDLER;

public class ChocolotlClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HandledScreens.register(TEMPERING_MACHINE_SCREEN_HANDLER, TemperingMachineScreen::new);
    }
}
