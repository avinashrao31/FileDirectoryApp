A simple Java console application that simulates a file system with folders and text files.
You can navigate through folders, list folder contents, create new files and folders, append text to files, and calculate the total byte size of the directory.

Methods
ls - List files and folders in the current directory
cd <folderName> - change the directory 
cd .. - go up to the parent directory
mkdir <folderName> - create a new subfolder
touch <fileName> - create a new file (real file saved on disk)
size - calculates total size of the current directory and subdirectories
exit - quit out of the program loop

Notes
Files created with touch are real files in the directory of the program, which are stored on the disk
write appends text and automatically adds a newline
The entire folder structure beyond the root is simulated in memory
