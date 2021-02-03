## Programs information

    From: University of Kent
    Name: Bin2VHDL
    Language: Java
    Date: August 2012


## Description

This README file details how to run a Bin2VHDL Program.

This program is used to convert the binary packet that it will receive as 
parameter, into VHDL language.


## How to use it

The Bin2VHDL program is very simple to understand, the only thing that you
need to give, is the binary file, then the program will automatically 
convert it into VHDL language, and output the result of this convertion
on the standard output. So then you just need to copy / paste the result
in your PicoBlaze program.

    ./bin2vhdl [-t template] [-n name] [-o outfile] [-w bitwidth] binary_file


## Further information

This program is no more used as a single program, we incorporated it
into the PicoBlazePacketForger, in order to be more easy and relevant
to use the whole java part.

## Contact

    David Carnot
    dc386@kent.ac.uk

    Jean-Charles Le Goff
    jcl28@kent.ac.uk

    Valentin Briand
    vb89@kent.ac.uk

    Michael Bishaey
    mb551@kent.ac.uk