LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;
USE IEEE.STD_LOGIC_UNSIGNED.ALL;

ENTITY ${name!filename} IS
    PORT (
        CS : IN STD_LOGIC;
        ADDR : IN STD_LOGIC_VECTOR (${addr_bits-1} DOWNTO 0);
        D_IO : INOUT STD_LOGIC_VECTOR(${word_width-1} DOWNTO 0));
END ENTITY;

ARCHITECTURE RTL OF ${name!filename} IS
    TYPE ROM_T IS ARRAY (0 TO ${(values?size)-1})OF STD_LOGIC_VECTOR(${word_width-1} DOWNTO 0);
    CONSTANT ROM_PTR : ROM_T := ( 
  <#list values as x>
 X"${hex(x,word_width)}"<#if (!(x?is_last))>,</#if><#if ((x?index)%20==0) && !(x?is_first) >
  </#if>
  </#list>
  );
BEGIN
    PROCESS (cs)
    BEGIN
        IF (cs = '0') THEN
            D_IO <= ROM_PTR(CONV_INTEGER(ADDR));
        END IF;
    END PROCESS;

END RTL;