package com.efimchick.ifmo.io.filetree;


import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class FileTreeImpl implements FileTree {

	@Override
	public Optional<String> tree(Path path) {
	    if (path == null || !Files.exists(path)) {
	        return Optional.empty();
	    }

	    StringBuilder sb = new StringBuilder();
	    buildTree(path, sb, "", "");
	    return Optional.of(sb.toString());
	}

	private void buildTree(Path path, StringBuilder sb, String prefix, String indent) {
		/*
		 * child - новый путь для построения дерева. 
		 * sb - объект StringBuilder, в который добавляется информация о файле или директории. 
		 * indent + childPrefix - префикс для форматирования текущего файла или директории. 
		 * indent + childIndent - отступ для форматирования следующих файлов или директорий.
		 */
	    try {
	        if (Files.isRegularFile(path)) {
	            sb.append(prefix).append(path.getFileName()).append(" ").append(Files.size(path)).append(" bytes\n");
	        } else if (Files.isDirectory(path)) {
	            sb.append(prefix).append(path.getFileName()).append(" ").append(calculateDirectorySize(path)).append(" bytes\n");
   
	            List<Path> contents = Files.list(path).collect(Collectors.toList());
	            List<Path> directories = new ArrayList<>();	            
	            List<Path> files = new ArrayList<>();

	            for (Path child : contents) {
	                if (Files.isDirectory(child)) {
	                    directories.add(child);
	                } else if (Files.isRegularFile(child)) {
	                    files.add(child);
	                }
	            }

	            sortFilesCaseInsensitive(directories);
	            sortFilesCaseInsensitive(files);

	            int totalFiles = directories.size() + files.size();

	            for (int i = 0; i < totalFiles; i++) {
	                Path child = (i < directories.size()) ? directories.get(i) : files.get(i - directories.size());
	                String childPrefix = (i == totalFiles - 1) ? "└─ " : "├─ ";
	                String childIndent = (i == totalFiles - 1) ? "   " : "│  ";
	                buildTree(child, sb, indent + childPrefix, indent + childIndent);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	private void sortFilesCaseInsensitive(List<Path> files) {
	    files.sort(Comparator.comparing(path -> path.getFileName().toString(), String.CASE_INSENSITIVE_ORDER));
	}
	
	private long calculateDirectorySize(Path dir) {
		DirectorySizeVisitor sizeVisitor = new DirectorySizeVisitor();
		try {
			Files.walkFileTree(dir, sizeVisitor);
		} catch (IOException e) {
			System.out.println("Error directory. "+ this +" " + e.getLocalizedMessage());
		}
		
		return sizeVisitor.getTotalSize();
	}
}

