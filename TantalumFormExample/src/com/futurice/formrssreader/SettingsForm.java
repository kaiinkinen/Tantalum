package com.futurice.formrssreader;

import com.futurice.tantalum3.log.L;
import com.futurice.tantalum3.rms.RMSUtils;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStoreFullException;

/**
 * Simple settings form for setting the RSS Feed URL
 *
 * @author ssaa
 */
public final class SettingsForm extends TextBox implements CommandListener {

    private final RSSReader midlet;
    private final Command saveCommand = new Command("Save", Command.OK, 0);
    private final Command backCommand = new Command("Back", Command.BACK, 0);

    public SettingsForm(final RSSReader midlet) {
        super("RSS Feed URL", "", 256, TextField.URL & TextField.NON_PREDICTIVE);
        setInitialInputMode("UCB_BASIC_LATIN");
        this.midlet = midlet;

        addCommand(saveCommand);
        addCommand(backCommand);
        setCommandListener(this);
    }

    public void setUrlValue(String url) {
        setString(url);
    }

    public String getUrlValue() {
        return getString();
    }

    public void commandAction(Command command, Displayable displayable) {

        if (command == saveCommand) {
            try {
                RMSUtils.write("settings", getString().getBytes());
            } catch (RecordStoreFullException ex) {
                L.e("Can not write settings", "", ex);
            }
            midlet.switchDisplayable(null, midlet.getList());
            midlet.getList().reload(true);
        } else if (command == backCommand) {
            midlet.switchDisplayable(null, midlet.getList());
        }
    }
}
