import os

def clean(text):
    return text.replace('\n', '')

def search(path, cb):
    for entry in os.scandir(path):
        if entry.is_dir():
            search(entry.path, cb)
        elif entry.is_file():
            cb(entry.path)

def optimize_file(path):
    if not path.endswith('.java'): return 

    text = "couldn't find text"
    with open(path, 'r') as f:
        text = clean(f.read())
    with open(path, 'w') as f:
        f.write(text)

def optimize_stuylib():
    search('./', optimize_file)

optimize_stuylib()