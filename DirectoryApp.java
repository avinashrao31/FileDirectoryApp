import java.util.Scanner;


//Main
public class DirectoryApp {
	public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);

	    //Create the root folder
	    FolderEntry root = new FolderEntry("root", null);
	    FolderEntry currentFolder = root;

	    System.out.println("Welcome to Directory Size Calculator!");
	    System.out.println("Commands: ls | cd <folderName> | cd .. | mkdir <folderName> | touch <fileName> | write <fileName> <text> | size | exit");

	    while (true) { //basic command loop
	        System.out.print(currentFolder.getName() + "> ");
	        String input = scanner.nextLine().trim();
	        if (input.isEmpty()) continue;

	        String[] parts = input.split(" ", 2);
	        String command = parts[0];

	        if (command.equals("exit")) {
	            break;
	        }

	        switch (command) {
	            case "ls": //list directory content
	                for (Entry e : currentFolder.getChildren()) {
	                    System.out.println(e.getName() + (e.isFolder() ? "/" : ""));
	                }
	                break;

	            case "cd": //change directory to specified directory or root folder
	                if (parts.length < 2) {
	                    System.out.println("Usage: cd <folderName> or cd ..");
	                    break;
	                }
	                String target = parts[1];
	                if (target.equals("..")) {
	                    if (currentFolder.getParent() != null) {
	                        currentFolder = currentFolder.getParent();
	                    } else {
	                        System.out.println("Already at root");
	                    }
	                } else {
	                    Entry e = currentFolder.getChildByName(target);
	                    if (e != null && e.isFolder()) {
	                        currentFolder = (FolderEntry) e;
	                    } else {
	                        System.out.println("Folder not found: " + target);
	                    }
	                }
	                break;

	            case "mkdir": //create new folder
	                if (parts.length < 2) {
	                    System.out.println("Usage: mkdir <folderName>");
	                    break;
	                }
	                String folderName = parts[1];
	                if (currentFolder.getChildByName(folderName) != null) {
	                    System.out.println("An entry with that name already exists.");
	                } else {
	                    FolderEntry newFolder = new FolderEntry(folderName, currentFolder);
	                    currentFolder.addEntry(newFolder);
	                    System.out.println("Folder created: " + folderName);
	                }
	                break;

	            case "touch": //create new file
	                if (parts.length < 2) {
	                    System.out.println("Usage: touch <fileName>");
	                    break;
	                }
	                String fileName = parts[1];
	                if (currentFolder.getChildByName(fileName) != null) {
	                    System.out.println("An entry with that name already exists.");
	                } else {
	                    FileEntry newFile = new FileEntry(fileName);
	                    currentFolder.addEntry(newFile);
	                    System.out.println("File created: " + fileName);
	                }
	                break;

	            case "write": //append text to existing file
	                if (parts.length < 2) {
	                    System.out.println("Usage: write <fileName> <text>");
	                    break;
	                }
	                String[] writeParts = parts[1].split(" ", 2);
	                if (writeParts.length < 2) {
	                    System.out.println("Usage: write <fileName> <text>");
	                    break;
	                }
	                String targetFileName = writeParts[0];
	                String textToWrite = writeParts[1];

	                Entry targetEntry = currentFolder.getChildByName(targetFileName);
	                if (targetEntry == null || targetEntry.isFolder()) {
	                    System.out.println("File not found: " + targetFileName);
	                } else {
	                    ((FileEntry) targetEntry).appendText(textToWrite);
	                }
	                break;

	            case "size": //calculate size of current directory and subdirectories
	                System.out.println("Total size: " + currentFolder.calculateTotalSize() + " bytes");
	                break;

	            default:
	                System.out.println("Unknown command: " + command);
	                break;
	        }
	    }

	    scanner.close();
	    System.out.println("Goodbye!");
	}
}
