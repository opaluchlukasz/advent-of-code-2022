package com.github.opaluchlukasz.adventofcode;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.eclipse.collections.api.block.procedure.primitive.ObjectLongProcedure;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectLongHashMap;

import java.util.List;
import java.util.TreeMap;

public class AdventOfCode014 {
    public static final long TOTAL_SPACE = 70_000_000L;
    public static final long REQUIRED_SPACE = 30_000_000L;

    public static void main(String[] args) {
        System.out.println(solve("input_007"));
    }

    static long solve(String fileName) {
        List<String> lines = FileUtils.read(fileName);
        return solve(lines);
    }

    private static long solve(List<String> commands) {
        Tree<FileSystemNode> fileSystem = new Tree<>(new Directory("/", null), null);
        Tree<FileSystemNode> current = fileSystem;
        for (String command : commands) {
            if (command.equals("$ ls")) {
                continue;
            } else if (command.equals("$ cd /")) {
                current = fileSystem;
            } else if (command.startsWith("$ cd ..")) {
                current = current.getParent();
            } else if (command.startsWith("$ cd ")) {
                String directory = command.replace("$ cd ", "");
                current = current.findInChildren(node -> node instanceof Directory && ((Directory) node).name.equals(directory));
            } else if (command.startsWith("dir ")) {
                current.addChild(new Directory(command.replace("dir ", ""), ((Directory) current.getData()).path));
            } else {
                String[] fileDesc = command.split(" ");
                current.addChild(new File(fileDesc[1], Long.parseLong(fileDesc[0])));
            }
        }

        ObjectLongHashMap<String> sizes = new ObjectLongHashMap<>();

        fileSystem.traverse(treeNode -> {
            if (treeNode.getData() instanceof File) {
                Long size = ((File) treeNode.getData()).size;
                while (treeNode.getParent() != null) {
                    sizes.addToValue(((Directory) treeNode.getParent().getData()).path, size);
                    treeNode = treeNode.getParent();
                }
            }
        });
        long available = TOTAL_SPACE - sizes.get("/");
        long needToFree = REQUIRED_SPACE - available;


        TreeMap<Long, String> sortedMap = new TreeMap<>();
        sizes.forEachKeyValue((ObjectLongProcedure<String>) (s, l) -> sortedMap.put(l, s));
        return sortedMap.ceilingEntry(needToFree).getKey();
    }

    static sealed class FileSystemNode { }

    @ToString
    @RequiredArgsConstructor
    static final class File extends FileSystemNode {
        private final String name;
        private final Long size;
    }

    @ToString
    static final class Directory extends FileSystemNode {
        private final String name;
        private final String path;

        Directory(String name, String parentPath) {
            this.name = name;
            this.path = getPath(name, parentPath);
        }

        private String getPath(String name, String parentPath) {
            if (parentPath == null) {
                return "/";
            }
            if (parentPath.equals("/")) {
                return "/" + name;
            }
            return parentPath + "/" + name;
        }
    }
}