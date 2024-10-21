#!/bin/sh

# Check if mistral is in the list of models
if ollama list | grep -q 'mistral'; then
  echo "mistral is already installed."
else
  echo "mistral not found. Pulling the model..."
  ollama pull mistral
fi

if ollama list | grep -q 'mxbai-embed-large'; then
  echo "mxbai-embed-large: is already installed."
else
  echo "mxbai-embed-large: not found. Pulling the model..."
  ollama pull mxbai-embed-large:
fi

# You can add other commands here or just start the main container process
