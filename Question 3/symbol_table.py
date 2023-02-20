import re

def create_symbol_table(asm_code):
    # Initialization
    symbol_table = {}
    pre_defined_symbols = {'SP': 0, 'LCL': 1, 'ARG': 2, 'THIS': 3, 'THAT': 4, 'SCREEN': 16384, 'KBD': 24576, 'R0': 0, 'R1': 1, 'R2': 2, 'R3': 3, 'R4': 4, 'R5': 5, 'R6': 6, 'R7': 7, 'R8': 8, 'R9': 9, 'R10': 10, 'R11': 11, 'R12': 12, 'R13': 13, 'R14': 14, 'R15': 15}
    symbol_table.update(pre_defined_symbols)
    address = 0

    # First pass
    lines = asm_code.split('\n')
    for line in lines :
        if '//' in line:
            line = line[:line.index('//')] #IGNORES THE COMMENTS
        if '(' in line:
            label = re.search(r'\(([^\s]+)\)', line).group(1)
            symbol_table[label] = address
        else:
            address += 1

    # Second pass
    address = 16
    for line in lines:
        if '//' in line:
            line = line[:line.index('//')]
        if '@' in line:
            symbol = re.search(r'@([^\s]+)', line).group(1)
            if symbol not in symbol_table:
                symbol_table[symbol] = address
                address += 1

    return symbol_table

asm_code = input("Please enter the asm file name including the extension: ")
with open(asm_code, 'r') as f:
    asm_code = f.read()

symbol_table = create_symbol_table(asm_code)
symbol_table_s = str(symbol_table)
output_file_name = 'symboltable.asm'
with open(output_file_name, 'w') as file:
    for symbol,value in symbol_table.items():
       file.write(f'{symbol}\t{value}\t\n')

