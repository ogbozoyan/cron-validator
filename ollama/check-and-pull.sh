#!/bin/sh

# Check if llama3.2 is in the list of models
if ollama list | grep -q 'llama3.2'; then
  echo "llama3.2 is already installed."
else
  echo "llama3.2 not found. Pulling the model..."
  ollama pull llama3.2
fi

if ollama list | grep -q 'nomic-embed-text'; then
  echo "nomic-embed-text is already installed."
else
  echo "nomic-embed-text not found. Pulling the model..."
  ollama pull nomic-embed-text
fi

# You can add other commands here or just start the main container process
