package com.efimchick.ifmo.io.filetree;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

public class FileTreeVisitor extends SimpleFileVisitor<Path>{
	private StringBuilder sb;
    private Deque<Integer> indentLevels;
    
	public FileTreeVisitor(StringBuilder sb) {
		super();
		this.sb = sb;
		this.indentLevels = new LinkedList<>();
	} 
	
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		addEntry(dir);
		indentLevels.push(Optional.ofNullable(indentLevels.peek()).orElse(0) + 1);
		/*
		 * В этом коде мы использовали Optional.ofNullable для обработки случая, 
		 * когда верхний элемент indentLevels равен null. Если верхний элемент не равен null, мы просто добавляем к нему 1. 
		 * В противном случае, когда верхний элемент равен null, 
		 * мы используем orElse(0) для установки значения по умолчанию равным 0. 
		 * Затем мы используем push для добавления обновленного значения в indentLevels.
		 */
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		addEntry(file);
		return FileVisitResult.CONTINUE;
	}
	
	
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {		
		if (!indentLevels.isEmpty()) {
		    indentLevels.pop();
		}
		return FileVisitResult.CONTINUE;
	}

	private void addEntry(Path path) {
		String entry = formatEntry(path);	
		boolean isLastEntry = isLastEntryInDirectory(path);
	    
	    sb.append(getIndentation(isLastEntry)).append(entry).append(System.lineSeparator());
	}

	private String formatEntry(Path path) {
		StringBuilder entry = new StringBuilder();
		
		long size = calculateDirectorySize(path);
		
		if(Files.isDirectory(path)) {
			entry.append(path.getFileName()).append(" ").append(size).append(" bytes");
		} else {
			try {
				entry.append(path.getFileName()).append(" ").append(Files.size(path)).append(" bytes");
			} catch (IOException e) {
				System.out.println("Error file. "+ this +" " + e.getLocalizedMessage());
			}
		}
		
		return entry.toString();
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
	
	private boolean isLastEntryInDirectory(Path path) {
	    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path.getParent())) {
	    	/*
	    	 * path.getParent() возвращает родительский каталог текущего элемента. 
	    	 * DirectoryStream используется для итерации по содержимому родительского каталога.
	    	 */
	        Iterator<Path> iterator = stream.iterator();  //для прохода по всем элементам в родительском каталоге.
	        while (iterator.hasNext()) {
	            Path next = iterator.next();
	            if (next.equals(path)) { //означает, что мы нашли текущий элемент в родительском каталоге.
	                return !iterator.hasNext();// мы проверяем, есть ли еще элементы в итераторе
	        //Если элементов больше нет, то текущий элемент является последним в родительском каталоге, и метод возвращает true.
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error directory stream. " + this + " " + e.getLocalizedMessage());
	    }
	    
	    return false;
	}
	
	private String getIndentation(boolean isLastEntry) {
	    StringBuilder indentation = new StringBuilder();

	    for (int i = 0; i < indentLevels.size() - 1; i++) {
	        Integer level = ((LinkedList<Integer>) indentLevels).get(i);
	        if (level != null && level > 0) {
	            indentation.append("│  ");
	        }
	    }

	    if (!indentLevels.isEmpty()) {
	        Integer lastLevel = indentLevels.peekLast();
	        if (lastLevel != null && lastLevel > 0) {
	            indentation.append(isLastEntry ? "└─ " : "├─ ");
	        }
	    }

	    return indentation.toString();
	}
	
}