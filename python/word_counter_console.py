"""
Name: word_counter_console.py
Author: Brandon Ragsdale
Date: 9/17/2015
Description: This script counts the number of times
a particular word occurs within a set of files.
"""

from pathlib import Path
import sys

# retrieve the arguments from
# the command line
args = sys.argv

try:
    # check if the user entered the path of the
    # directory and at least one file type
    if len(args) >= 3:
        # retrieve the argument that contains
        # the directory path from the command line
        # and initialize the counter
        directory_path = args[1]
        word = args[2]

        # search through the directory path recursively
        for directory_file in Path(directory_path).glob('**/*.*'):
            # split directory_file on the periods
            # to find out what file type it is
            directory_file_split = str(directory_file).split('.')
            file_type = directory_file_split[len(directory_file_split) - 1]
            line_counter = 0
            word_counter = 0

            # if the file type is found in the
            # command line arguments, open the file
            if file_type in args:
                # open the file and show the
                # file path to the user
                f = open(str(directory_file))
                directory_file_name = str(directory_file)
                print(directory_file_name + ":")

                for line in f:
                    line_counter += 1

                    if word not in line:
                        continue

                    # increment each time the word is found
                    word_counter += 1
                    print(word + " found at line " + str(line_counter) + ".")

                # display the total number of times the word
                # was found in the file
                print(word + " was found " + str(word_counter) + " times.")
    else:
        # if they didn't put any arguments in,
        # let them know how to use the script
        print('Usage: python3 word_counter_console.py [path_of_directory] [word] [file_type] [file_type] [etc.]')
        print('Example: python3 word_counter_console.py ~/Documents wordToSearch txt log')
except Exception:
    # if the user attempts to find the file count of a
    # non-text-based file type, like a png file,
    # raise an exception
    raise
