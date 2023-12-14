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
1)Path Handling:
The cd command supports navigating to parent directories and specified directories.
The ls command lists the contents of the current directory or a specified directory.
2)File Content:
Files store content using the content attribute.
The cat command displays the content of a file.


for testing the commands
1)mkdir new_folder

2)cd new_folder

3)mkdir folder1
4)mkdir folder2
5)mkdir folder3

6)cd .. changes to new_folder

7)ls : output
folder1 new_folder
folder2 new_folder
folder3 new_folder

8)touch new_file.txt

9)echo "Hello, World!" > /documents/new_file.txt

10)cat document.txt

11)mv /documents/new_file.txt /backup/new_file_backup.txt

12)cp /documents/important_document.txt /backup/documents_backup.txt

13)rm /documents/unwanted_file.txt

14)exit
