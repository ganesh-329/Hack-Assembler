import re

def remove_whitespace(file_name):
    # Open the input file
    with open(file_name, 'r') as file:
        # Read the file
        lines = file.readlines()

    # Remove whitespace using regular expressions
    processed_lines = []
    for line in lines:
        line = re.sub(r'\s', '', line) # remove whitespace
        line = re.sub(r'//.*', '', line) # remove comments
        if line :
            processed_lines.append(line)

    # Open the output file
    output_file_name = 'no_whitespace_' + file_name
    with open(output_file_name, 'w') as file:
        # Write the lines to the output file
        for line in processed_lines:
             file.writelines(line + '\n')

# Test the function
file_name = input("Enter the asm file name with extension :")
remove_whitespace(file_name)
