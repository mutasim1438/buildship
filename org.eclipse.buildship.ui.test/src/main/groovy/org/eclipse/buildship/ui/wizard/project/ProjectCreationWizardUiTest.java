/*
 * Copyright (c) 2015 the original author or authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Simon Scholz (vogella GmbH) - initial API and implementation and initial documentation
 */

package org.eclipse.buildship.ui.wizard.project;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

import org.eclipse.buildship.ui.AbstractSwtbotTest;

/**
 * SWTBot tests for the {@link ProjectCreationWizard}.
 *
 */
public class ProjectCreationWizardUiTest extends AbstractSwtbotTest {

    @Test
    public void canOpenNewWizardFromMenuBar() throws Exception {
        // open the import wizard
        bot.menu("File").menu("New").menu("Other...").click();
        SWTBotShell shell = bot.shell("New");
        shell.activate();
        bot.tree().expandNode("Gradle").select("Gradle Project");
        bot.button("Next >").click();

        // if the wizard was opened the label is available, otherwise a WidgetNotFoundException is
        // thrown
        bot.text(ProjectWizardMessages.InfoMessage_NewGradleProjectWizardPageDefault);

        // cancel the wizard
        bot.button("Cancel").click();
    }

    @Test
    public void defaultLocationButtonInitiallyChecked() throws Exception {
        // open the import wizard
        bot.menu("File").menu("New").menu("Other...").click();
        SWTBotShell shell = bot.shell("New");
        shell.activate();
        bot.tree().expandNode("Gradle").select("Gradle Project");
        bot.button("Next >").click();

        // check whether the checkbox is initially checked
        SWTBotCheckBox useDefaultLocationCheckBox = bot.checkBox(ProjectWizardMessages.Button_UseDefaultLocation, 0);
        assertTrue(useDefaultLocationCheckBox.isChecked());

        // cancel the wizard
        bot.button("Cancel").click();
    }

}