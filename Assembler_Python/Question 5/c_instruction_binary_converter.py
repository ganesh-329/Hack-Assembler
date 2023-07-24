def c_instruction_to_binary(instruction):
    # Define the lookup tables for the dest, comp, and jump fields
    dest_table = {
        '': '000',
        'M': '001',
        'D': '010',
        'MD': '011',
        'A': '100',
        'AM': '101',
        'AD': '110',
        'AMD': '111'
    }

    comp_table = {
        '0': '0101010',
        '1': '0111111',
        '-1': '0111010',
        'D': '0001100',
        'A': '0110000',
        '!D': '0001101',
        '!A': '0110001',
        '-D': '0001111',
        '-A': '0110011',
        'D+1': '0011111',
        'A+1': '0110111',
        'D-1': '0001110',
        'A-1': '0110010',
        'D+A': '0000010',
        'D-A': '0010011',
        'A-D': '0000111',
        'D&A': '0000000',
        'D|A': '0010101',
        'M'  : '1110000',
        '!M' : '1110001',
        '-M' : '1110011',
        'M+1': '1110111',
        'M-1': '1110010',
        'D+M': '1000010',
        'D-M': '1010011',
        'M-D': '1000111',
        'D&M': '1000000',
        'D|M': '1010101'
    }

    jump_table = {
        '': '000',
        'JGT': '001',
        'JEQ': '010',
        'JGE': '011',
        'JLT': '100',
        'JNE': '101',
        'JLE': '110',
        'JMP': '111'
    }

    # Split the instruction into dest, comp, and jump fields
    parts = instruction.split(';')
    if '=' in parts[0]:
        dest = parts[0].split('=')[0]
        comp = parts[0].split('=')[1]
    else:
        dest = ''
        comp = parts[0]
    jump = '' if len(parts) == 1 else parts[1]

    # Use the lookup tables to find the binary representations of the dest, comp, and jump fields
    dest_bits = dest_table[dest]
    comp_bits = comp_table[comp]
    jump_bits = jump_table[jump]

    # Concatenate the binary representations to form the final 16-bit binary instruction
    binary = '111' + comp_bits + dest_bits + jump_bits

    return binary

    
def assemble_file(input_file_name):
    with open(input_file_name, 'r') as input_file:
        lines = input_file.readlines()
        
    processed_lines = []
    for line in lines:
        line = line.strip()
        if line and line[0] not in ['@', '('] and line.strip():
            binary = c_instruction_to_binary(line)
            processed_lines.append(binary)     
            
    output_file_name = 'c_instruction.hack'
    with open(output_file_name, 'w') as output_file:
         output_file.write('\n'.join(processed_lines))

file_name = input("Enter the asm file name with extension : ")
assemble_file(file_name)
