// Computes RAM[1] = 1 + ... + RAM[0]  
    @i
    M=1    // i =1  
    @sum
    M=0    // sum =0
    
    
    
    
(LOOP)
    @i     // if i>RAM[0] gotoSTOP
    D=M        // sum +=i//i++(LOOP)@i  
    @R0  
    D=D-M  
    @STOP  
    D;JGT
    @i     // sum += i
    D=M
    @sum  
    M=D+M
    @i     // i++ 
    M=M+1
    @LOOP  //goto LOOP
    0;JMP
(STOP)
    @sum  
    D=M
    @R1
    M=D    // RAM[1] = thesum  
(END)
   @END  
   0;JMP
