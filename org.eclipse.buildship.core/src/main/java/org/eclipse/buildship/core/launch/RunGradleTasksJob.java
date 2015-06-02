/*
 * Copyright (c) 2015 the original author or authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Etienne Studer & Donát Csikós (Gradle Inc.) - initial API and implementation and initial documentation
 */

package org.eclipse.buildship.core.launch;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.gradleware.tooling.toolingclient.BuildLaunchRequest;
import com.gradleware.tooling.toolingclient.GradleDistribution;
import com.gradleware.tooling.toolingclient.LaunchableConfig;
import org.eclipse.buildship.core.CorePlugin;
import org.eclipse.buildship.core.util.collections.CollectionsUtils;
import org.eclipse.buildship.core.util.progress.ToolingApiJob;
import org.eclipse.core.runtime.IProgressMonitor;

import java.io.File;
import java.util.List;

/**
 * Launches a Gradle build with a given set of tasks.
 */
public final class RunGradleTasksJob extends ToolingApiJob {

    private final GradleRunConfigurationAttributes configurationAttributes;

    public RunGradleTasksJob(List<String> tasks, File projectDir, GradleDistribution gradleDistribution, File gradleUserHome, File javaHome, String jvmArguments, String arguments) {
        super("Launching Gradle tasks");

        this.configurationAttributes = GradleRunConfigurationAttributes.with(
                ImmutableList.copyOf(tasks),
                Preconditions.checkNotNull(projectDir).getAbsolutePath(),
                gradleDistribution,
                gradleUserHome != null ? gradleUserHome.getAbsolutePath() : null,
                javaHome != null ? javaHome.getAbsolutePath() : null,
                CollectionsUtils.splitBySpace(jvmArguments),
                CollectionsUtils.splitBySpace(arguments),
                false, false);
    }

    @Override
    protected void runToolingApiJob(IProgressMonitor monitor)  {
        List<String> tasks = this.configurationAttributes.getTasks();
        File workingDir = this.configurationAttributes.getWorkingDir();
        GradleDistribution gradleDistribution = this.configurationAttributes.getGradleDistribution();
        File gradleUserHome = this.configurationAttributes.getGradleUserHome();
        File javaHome = this.configurationAttributes.getJavaHome();
        ImmutableList<String> jvmArguments = this.configurationAttributes.getJvmArguments();
        ImmutableList<String> arguments = this.configurationAttributes.getArguments();

        // start tracking progress
        monitor.beginTask(String.format("Launch Gradle tasks %s", tasks), IProgressMonitor.UNKNOWN);

        // configure the request
        BuildLaunchRequest request = CorePlugin.toolingClient().newBuildLaunchRequest(LaunchableConfig.forTasks(tasks));
        request.projectDir(workingDir);
        request.gradleDistribution(gradleDistribution);
        request.gradleUserHomeDir(gradleUserHome);
        request.javaHomeDir(javaHome);
        request.jvmArguments(jvmArguments.toArray(new String[jvmArguments.size()]));
        request.arguments(arguments.toArray(new String[arguments.size()]));

        // launch the build
        request.executeAndWait();
    }

}
