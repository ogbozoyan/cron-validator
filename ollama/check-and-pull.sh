#!/bin/sh

# Check if mistral is in the list of models
if ollama list | grep -q 'mistral'; then
  echo "mistral is already installed."
else
  echo "mistral not found. Pulling the model..."
  ollama pull mistral
fi

if ollama list | grep -q 'nomic-embed-text'; then
  echo "nomic-embed-text is already installed."
else
  echo "nomic-embed-text not found. Pulling the model..."
  ollama pull nomic-embed-text
fi

# You can add other commands here or just start the main container process
