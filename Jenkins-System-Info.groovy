package com.mukesh.singal

import com.thoughtworks.xstream.mapper.Mapper
import hudson.model.*
import jenkins.model.Jenkins
import org.jenkins.plugins.lockableresources.LockableResources
import org.jenkins.plugins.lockableresources.LockableResource


Jenkins instance = Jenkins.getInstance()

/* call all Avaiable Functions */
//Running Executor
//getCurrentBuildNodeName()
//getCurrentBuildParamters()
//getNumberOfExecutorInCurrentSlave()


//Jenkins related
//getLastBuildLogs("createProject",instance)
//getJenkinsInfo(instance)

//getAllItemNames(instance)
//getAllFolderNames(instance)
//getAllJobsNames(instance)

//getNodeList(instance)
getLabelList(instance)
//getAllJobs(instance)
//getExecutorsInfo()

//Special
//getLockableResources()

/* Get All jobs and Build Numbers */
void getAllJobs(Jenkins instance){
    println("------------------------ Get All Jobs ---------------------------")
    instance.getAllItems().each{ item->
        Job job = item instanceof com.cloudbees.hudson.plugins.folder.Folder? null : item

        if(job && !job.getBuilds().isEmpty()){
            println("Job Name :                 "+job?.displayName)
            println("Job type :                 "+job.toString())
            println("Last Build Number :        "+job?.lastBuild?.displayName)
            println("Last Build Status          " + job?.lastBuild?.result)
            println("Build Status Summary :     "+job?.lastBuild?.buildStatusSummary?.message)
            println("Last Build Stated on       "+job?.lastBuild?.time?.dateString)
            println("Last Build Duration :      "+job?.lastBuild?.durationString)
            println("Last Build Dir             "+job?.lastBuild?.rootDir)
            println("Last Build cause :         ")
            job?.lastBuild?.getCauses().each { cause ->
                println("                           "+cause.getShortDescription())
            }
            println("Characterstic Information ")
            job?.lastBuild?.characteristicEnvVars.each{
                println("                           "+it.key + "   ->    "+it.value)
            }
            println("--------------------------------")
        }
    }
}


void getObjectInfo(obj){
    println("----------------------------------------------------")
    if(obj == null){
        println("Object is null")
    }
    println("Class Name :"+ obj.class.name)
    println("\nProperties")
    obj.metaClass.properties.each{ p->
        if(p.getter){
            def out = new StringBuffer()
            out << 'Name : ' + p.name.toString().padRight(30)
            out << 'Property : ' + p.getProperty(obj).toString().padRight(30)
            out << 'Type : ' + p.type.toString()

            println(out.toString())
        }
    }
    println("\nMethods")
    obj.class.getDeclaredMethods().each{ m->
        println("    ->   ${m.toString()}")
    }
}

/**  Get Job last Build logs
 *   @Params Job object
 *   @instance Jenkins Instance
 */

void getLastBuildogs(String jobName, Jenkins instance){
    instance.getAllItems().each{ item->
        Job job = item instanceof com.cloudbees.hudson.plugins.folder.Folder? null : item
        if(job){
            if (job.displayName == jobName){
                Run lastBuild = job.getLastBuild()
                if(lastBuild){
                    file = lastBuild.getLogFile()
                    println(file.getText())
                }
            }
        }
    }
}
//Get List of all avaible lockable resources
void getLockableResources() {
    println("------------------------ Lockable resources avaiable ---------------------------")
    def resourceList = new LockableResources().resources
    resourceList.each { x ->
        println("   " + x.getName())
    }

}

/* Get all jobs and folder names */
void getAllItemNames(Jenkins instance) {
    println("--------------------- Get all available items: Folder and Jobs ------------------------------")
    instance.getAllItems().each { item ->
        println("FullDisplayName " + item.getFullDisplayName())
    }
}

/*  Get all job names */
void getAllJobsNames(Jenkins instance) {
    println("--------------------- All Job Names ------------------------------")
    x = instance.getAllItems()
    instance.getJobNames().each { job ->
        println("  * " + job)
    }
}

/*  Get all Folder names */
void getAllFolderNames(Jenkins instance) {
    println("--------------------- All Folder Names ------------------------------")
    
    instance.getAllItems().each { item ->
        if (item instanceof com.cloudbees.hudson.plugins.folder.Folder) {
            println(item.getFullDisplayName())
        }
    }
}


/* Get current build paramters */
void getCurrentBuildParamters() {
    println("--------------------- Current Build Parameter ------------------------------")

    Build build = Executor.currentExecutor().currentExecutable
    ParametersAction parametersAction = build.getAction(ParametersAction)
    parametersAction?.parameters.each { ParameterValue value ->
        println value
    }
}

/* Get Jenkins Information */
void getJenkinsInfo(Jenkins instance){
    println("--------------------- Jenkins Info ------------------------------")
    println("Jenkins Verion is - "+ instance.getVersion())
    println("Jenkins Root Directory - "+ instance.getRootPath())
}

/*  get list of Avaiable Nodes in Jenkins */
void getNodeList(Jenkins instance) {
    println("--------------------- List of Available Nodes ------------------------------")
    instance.computers.each { comp ->
        println("  " + comp.displayName + "     isOffline -> " + comp.isOffline())
    }
}



/* Get list of label assigned to Nodes */
void getLabelList(Jenkins instance){
    println("--------------------- List of Available Labels ------------------------------")
    instance.getC
    instance.getLabels().each {
        println(it.getName() )
        getObjectInfo(it)
        it.getNodes().each{ node->
            println("   ->    "+    node.getNodeName())
        }
    }
}



/* Get Current build Node name */
void getCurrentBuildNodeName() {
    println("--------------------- Current Node Name ------------------------------")
    println(Computer.currentComputer().getDisplayName())
}

/* Get Number of Executors in Current Slave*/
void getNumberOfExecutorInCurrentSlave() {
    println("--------------------- Number of Executer in Current Slave ------------------------------")
    println(Computer.currentComputer().countExecutors())
}


/* Get All Executors Informations */
void getExecutorsInfo() {
    println("--------------------- Number of Executer in Current Slave ------------------------------")
    executors = hudson.model.Computer.currentComputer().getDisplayExecutors()
    for (i in executors) {
        println("   Display Name of Executor - " + i.getDisplayName())
        println("   Display url of Executor - " + i.getUrl())
        println("   Hash Code of Executor - " + i.hashCode())
        println("---------------------------------------------------")
    }
}