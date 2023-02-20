def a_instruction_to_binary(instruction):
    address = int(instruction[1:])
    binary = '0' + format(address, '015b')
    return binary
    
def read_symbol_table(symbol_table_file_name):
    symbol_table = {}
    with open(symbol_table_file_name, 'r') as symbol_table_file:
         lines = symbol_table_file.readlines()
            
    for line in lines:
        symbol, address = line.strip().split('\t')
        symbol_table[symbol] = int(address)
        
    return symbol_table
    
        
def assemble_file(input_file_name, symbol_table):
    with open(input_file_name, 'r') as input_file:
        lines = input_file.readlines()
        
    processed_lines = []
    next_address = 16
    for line in lines:
        line = line.strip()
        if line and line[0] == '@':
            symbol = line[1:]
            if symbol.isdigit():
                address = int(symbol)
            elif symbol in symbol_table:
                address = symbol_table[symbol]
            else:
                symbol_table[symbol] = next_available_address
                address = next_available_address
                next_available_address += 1
            binary = a_instruction_to_binary(f'@{address}')
            processed_lines.append(binary) 
                       
    output_file_name = 'a_instruction.hack'
    with open(output_file_name, 'w') as output_file:
         output_file.write('\n'.join(processed_lines))        

file_name = input("Enter the file name with extension : ")
symbol_table_file_name = input("Enter the symbol table file name with extension : ")
symbol_table = read_symbol_table(symbol_table_file_name)
assemble_file(file_name, symbol_table)
