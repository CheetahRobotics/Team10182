# Getting started with Git

Git is a free and open source distributed version control system designed to handle everything from small to very large projects with speed and efficiency.

#### Step 1: Installation:

Git should already be installed on your laptop. If not, you can install it from: https://git-scm.com/downloads

You can tell if it is installed by opening a command prompt (\*) and typing 'git'. If it says "'gita' is not recognized as an internal or external command, operable program or batch file.", then git is NOT installed.

####Step 2: Create directory where your code will live.

There should be a directory on your C drive already: c:\code\FTC\Tean10182. If there is not one, then create it, with Windows explorer for instance. (\*\*)

####Step 3: Download code from Git.

Open the command prompt (*) and use cd to go to the directory you just created

    c:
    cd \code\FTC\Team10182

Then issue the following commands to git:

    git init
    git remote add origin https://github.com/rwoodley/Team10182
    git pull origin master
    
Then if you list the directory contents (type `dir`) you'll see the code has been down-loaded.

(\*) To open a command prompt on Windows, hold down the Windows key and press **R**. Type *cmd* in the little box that pops up.
(\*\*) Open explorer by holding down the Windows key and pressing **E**.

