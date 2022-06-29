#!/usr/bin/env python3

from pathlib import Path
from subprocess import run

def err(msg):
    print(msg)
    exit(1)

def exec(*args, **kwargs):
    run(*args, **kwargs, shell=True)

# Verify runtime environment
if Path(__file__).parent != Path("."):
    err("Run the fuzzer from the correct directory!")
if __name__ != "__main__":
    err("Run the script directly from the command line!")

exec("make clean && make")

output_dir = "./alloc"
sample_dir = "./outputs"
exec(f"mkdir -p {output_dir}")
for file in Path(sample_dir).iterdir():
    fname = file.name
    args = fname.split("-")
    args[0] = "./testCache"
    cmd = " ".join(args)

    exec(f"{cmd} > {output_dir}/{fname}")
    exec(f"diff --color=auto -us {file} {output_dir}/{fname}")
