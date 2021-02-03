; This .COE file specifies initialization values for a block 
; memory of depth=16, and width=8. In this case, values are 
; specified in hexadecimal format.
memory_initialization_radix=16;
memory_initialization_vector=
 <#list values as x>
   ${hex(x,word_width)}<#if (!(x?is_last))>,</#if>
  </#list>
;
