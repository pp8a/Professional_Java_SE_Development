package com.efimchick.ifmo.io.filetree;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

public class FileTreeImpl implements FileTree {

	@Override
	public Optional<String> tree(Path path) {		
		if (path == null || !Files.exists(path)) {
		    return Optional.empty();
		}
		
		StringBuilder sb = new StringBuilder();
		
		FileTreeVisitor treeVisitor = new FileTreeVisitor(sb);
		try {
			Files.walkFileTree(path, treeVisitor);
		} catch (IOException e) {
			System.out.println("Error Files. "+ this + " "+ e.getLocalizedMessage());
		}
		
		return Optional.of(sb.toString());
	}

}