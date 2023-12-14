import java.util.*;

class TreeNode {
    String name;
    char type;  // 'd' for directory, '-' for file
    TreeNode parent;
    List<TreeNode> children;
    String content;  // Content for file type

    // Constructor for a directory
    TreeNode(String n, char t) {
        name = n;
        type = t;
        parent = null;
        children = new ArrayList<>();
    }

    // Function to find a child node by name
    TreeNode findChild(String childName) {
        for (TreeNode child : children) {
            if (child.name.equals(childName)) {
                return child;
            }
        }
        return null;
    }
}

class FileSystem {
    TreeNode root;
    TreeNode currentDir;
    String path;
    List<TreeNode> allNodes;

    FileSystem() {
        root = new TreeNode("/", 'd');
        allNodes = new ArrayList<>();
        allNodes.add(root);
        currentDir = root;
        path = "";
    }

    void mkdir(String name) {
        // Check if the directory already exists
        if (currentDir.findChild(name) != null) {
            System.out.println("mkdir: cannot create directory '" + name + "': File exists");
            return;
        }

        // Create a new directory
        TreeNode newDir = new TreeNode(name, 'd');
        currentDir.children.add(newDir);
        newDir.parent = currentDir;
        allNodes.add(newDir);
    }

    void cd(String dest) {
        if (dest.equals("..")) {
            if (currentDir.parent != null) {
                currentDir = currentDir.parent;
                path = currentDir.name;
            }
            return;
        }

        Queue<String> q = new LinkedList<>();
        String[] parts = dest.split("/");
        q.addAll(Arrays.asList(parts));

        while (!q.isEmpty()) {
            String curr = q.poll();

            if (curr.equals("..")) {
                if (currentDir.parent != null) {
                    currentDir = currentDir.parent;
                    path = currentDir.name;
                }
            } else {
                TreeNode child = currentDir.findChild(curr);
                if (child != null) {
                    currentDir = child;
                    path = path + "/" + currentDir.name;
                } else {
                    System.out.println("The system cannot find the path specified.");
                    break;
                }
            }
        }
    }

    void ls(String dest) {
        TreeNode curr;
        if (dest.equals("")) {
            curr = currentDir;
        } else {
            curr = allNodes.stream()
                    .filter(node -> node.name.equals(dest))
                    .findFirst()
                    .orElse(null);
        }

        if (curr != null) {
            for (TreeNode child : curr.children) {
                System.out.println(child.name + " " + child.type);
            }
        }
    }

    void cat(String fileName) {
        TreeNode file = currentDir.findChild(fileName);
        if (file != null && file.type == '-') {
            System.out.println(file.content);
        } else {
            System.out.println("cat: " + fileName + ": No such file or directory");
        }
    }

    void touch(String fileName) {
        if (currentDir.findChild(fileName) == null) {
            TreeNode newFile = new TreeNode(fileName, '-');
            currentDir.children.add(newFile);
            newFile.parent = currentDir;
            allNodes.add(newFile);
        } else {
            System.out.println("touch: " + fileName + ": File already exists");
        }
    }

    void echo(String text, String path, String fileName) {
        TreeNode file = currentDir;

        // Traverse the path to find the target directory
        Queue<String> q = new LinkedList<>(Arrays.asList(path.split("/")));

        while (!q.isEmpty()) {
            String curr = q.poll();

            if (curr.equals("..")) {
                if (file.parent != null) {
                    file = file.parent;
                }
            } else {
                TreeNode child = file.findChild(curr);
                if (child != null) {
                    file = child;
                } else {
                    System.out.println("The system cannot find the path specified.");
                    return;
                }
            }
        }

        // Check if the file exists in the target directory
        TreeNode targetFile = file.findChild(fileName);
        if (targetFile != null && targetFile.type == '-') {
            targetFile.content = text;
        } else {
            System.out.println("echo: " + fileName + ": No such file or directory");
        }
    }

    void mv(String sourcePath, String destinationPath) {
        TreeNode source = currentDir.findChild(sourcePath);
        TreeNode destination = currentDir.findChild(destinationPath);

        if (source != null && destination != null && destination.type == 'd') {
            source.parent.children.remove(source);
            source.parent = destination;
            destination.children.add(source);
        } else {
            System.out.println("mv: Invalid source or destination");
        }
    }

    void cp(String sourcePath, String destinationPath) {
        TreeNode source = currentDir.findChild(sourcePath);
        TreeNode destination = currentDir.findChild(destinationPath);

        if (source != null && destination != null && destination.type == 'd') {
            TreeNode copy = new TreeNode(source.name, source.type);
            copy.content = source.content;
            copy.parent = destination;
            destination.children.add(copy);
            allNodes.add(copy);
        } else {
            System.out.println("cp: Invalid source or destination");
        }
    }

    void rm(String targetPath) {
        TreeNode target = currentDir.findChild(targetPath);

        if (target != null) {
            if (target.type == 'd') {
                // Remove directory and its contents
                allNodes.remove(target);
            } else {
                // Remove file
                target.parent.children.remove(target);
                allNodes.remove(target);
            }
        } else {
            System.out.println("rm: " + targetPath + ": No such file or directory");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print((fileSystem.currentDir.name.equals("/") ? fileSystem.path : fileSystem.currentDir.name) + ">");
            String command = scanner.nextLine();

            String[] cmdArgs = command.split("\\s+");
            List<String> argsList = new ArrayList<>(Arrays.asList(cmdArgs));

            switch (argsList.get(0)) {
                case "cd":
                    if (argsList.size() == 2) {
                        fileSystem.cd(argsList.get(1));
                    } else {
                        System.out.println("Invalid usage. Example: cd <directory>");
                    }
                    break;
                case "mkdir":
                    if (argsList.size() >= 2) {
                        for (int i = 1; i < argsList.size(); i++) {
                            fileSystem.mkdir(argsList.get(i));
                        }
                    } else {
                        System.out.println("Invalid usage. Example: mkdir <directory> [<directory> ...]");
                    }
                    break;
                case "ls":
                    fileSystem.ls(argsList.size() > 1 ? argsList.get(1) : "");
                    break;
                case "cat":
                    if (argsList.size() == 2) {
                        fileSystem.cat(argsList.get(1));
                    } else {
                        System.out.println("Invalid usage. Example: cat <file>");
                    }
                    break;
                case "touch":
                    if (argsList.size() == 2) {
                        fileSystem.touch(argsList.get(1));
                    } else {
                        System.out.println("Invalid usage. Example: touch <file>");
                    }
                    break;
                case "echo":
                    if (argsList.size() == 4 && argsList.get(2).equals(">")) {
                        fileSystem.echo(argsList.get(1), argsList.get(3), argsList.get(4));
                    } else {
                        System.out.println("Invalid usage. Example: echo <text> > <path>/<file>");
                    }
                    break;
                case "mv":
                    if (argsList.size() == 3) {
                        fileSystem.mv(argsList.get(1), argsList.get(2));
                    } else {
                        System.out.println("Invalid usage. Example: mv <source> <destination>");
                    }
                    break;
                case "cp":
                    if (argsList.size() == 3) {
                        fileSystem.cp(argsList.get(1), argsList.get(2));
                    } else {
                        System.out.println("Invalid usage. Example: cp <source> <destination>");
                    }
                    break;
                case "rm":
                    if (argsList.size() == 2) {
                        fileSystem.rm(argsList.get(1));
                    } else {
                        System.out.println("Invalid usage. Example: rm <target>");
                    }
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }
    }
}
