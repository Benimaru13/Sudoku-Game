#!/bin/bash

# Clean old docs
rm -rf docs

# Generate new docs
javadoc -d docs \
        -sourcepath src \
        -subpackages game \
        -author \
        -version \
        -private \
        -windowtitle "Sudoku Game Documentation" \
        -doctitle "Sudoku Game API Documentation" \
        -header "Sudoku v1.0" \
        -charset UTF-8

# Open in browser (macOS - change for your OS)
open docs/index.html

echo "✅ Documentation generated successfully!"
