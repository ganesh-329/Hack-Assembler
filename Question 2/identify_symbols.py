import re

def identify_symbols(file_name):
    symbol_list = []
    # Open the input file
    with open(file_name, 'r') as file:
        lines = file.readlines()
    for line in lines:
        # Search for label symbols in parentheses
        symbols = re.findall(r'\(([^)]+)\)', line)
        for symbol in symbols:
            if symbol not in symbol_list:
                symbol_list.append(symbol)
        # Search for variable symbols after @
        symbols = re.findall(r'@([a-z0-9]+)', line)
        for symbol in symbols:
            if symbol not in symbol_list and symbol not in ['SP', 'LCL', 'ARG', 'THIS', 'THAT', 'SCREEN', 'KBD', 'R0', 'R1', 'R2', 'R3', 'R4', 'R5', 'R6', 'R7', 'R8', 'R9', 'R10', 'R11', 'R12', 'R13', 'R14', 'R15']:
                symbol_list.append(symbol)
    output_file_name = 'symbolsandvariables.asm'            
    with open(output_file_name, 'w') as file:
        for symbol in symbol_list:
            file.write(symbol + '\n')

# Test the function
file_name = input("Enter the asm file name with extension : ")
identify_symbols(file_name)
