1)TreeNode Class:
Represents a node in the file system tree.
Contains information about the node's name, type (directory or file), parent, children, and content (for files).
2)FileSystem Class:
Manages the overall file system.
Keeps track of the root directory, current directory, path, and all nodes in the system.
Provides methods for commands such as mkdir, cd, ls, cat, touch, echo, mv, cp, and rm.
3)Main Function:
Runs an infinite loop to accept and process user commands.
Parses user input to extract command and arguments.
Calls appropriate methods of the FileSystem class based on the user command.

Data Structures Used:
1)Tree Data Structure:
Represented using the TreeNode class.
Each node has a name, type, parent, children, and content.
Allows efficient representation of the hierarchical structure of directories and files.
Design Decisions:
2)Path Handling:
The cd command supports navigating to parent directories and specified directories.
The ls command lists the contents of the current directory or a specified directory.
3)File Content:
Files store content using the content attribute.
The cat command displays the content of a file.
for testing the commands
mkdir new_folder
cd new_folder
mkdir folder1
mkdir folder2
mkdir folder3
cd .. changes to new_folder
ls
folder1 new_folder
folder2 new_folder
folder3 new_folder
touch new_file.txt
echo "Hello, World!" > /documents/new_file.txt
cat document.txt
mv /documents/new_file.txt /backup/new_file_backup.txt
cp /documents/important_document.txt /backup/documents_backup.txt
rm /documents/unwanted_file.txt
exit
