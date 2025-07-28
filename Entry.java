import java.io.*;
import java.util.*;


//Base abstract class
abstract class Entry {
    protected String name;

    public Entry(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean isFolder();
}


//FileEntry class
class FileEntry extends Entry {
    private File file;

    public FileEntry(String filePath) {
        super(new File(filePath).getName());
        this.file = new File(filePath);

        try {
            if (!file.exists()) {
                file.createNewFile(); //create file
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

    public long getSize() {
        if (file.exists() && file.isFile()) {
            return file.length();
        }
        return 0;
    }

    @Override
    public boolean isFolder() {
        return false;
    }

    //append text to file
    public void appendText(String content) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(content);
            writer.write(System.lineSeparator()); // add newline
            System.out.println("Text written to " + file.getName());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}


//FolderEntry class
class FolderEntry extends Entry {
    private List<Entry> children;
    private FolderEntry parent; //to allow cd ..

    public FolderEntry(String name, FolderEntry parent) {
        super(name);
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    @Override
    public boolean isFolder() {
        return true;
    }

    public FolderEntry getParent() {
        return parent;
    }

    public void addEntry(Entry entry) {
        children.add(entry);
    }

    public List<Entry> getChildren() {
        return children;
    }

    public Entry getChildByName(String name) {
        for (Entry e : children) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public long calculateTotalSize() {
        long total = 0;
        for (Entry e : children) {
            if (e instanceof FileEntry) {
                total += ((FileEntry) e).getSize();
            } else if (e instanceof FolderEntry) {
                total += ((FolderEntry) e).calculateTotalSize();
            }
        }
        return total;
    }
}


