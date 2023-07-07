package com.efimchick.ifmo.io.filetree;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DirectorySizeVisitor extends SimpleFileVisitor<Path>{
	private long totalSize;

	public long getTotalSize() {
		return totalSize;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		totalSize+=attrs.size();
		return FileVisitResult.CONTINUE;
	}
}