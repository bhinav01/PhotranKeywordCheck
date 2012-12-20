MODULE asynchronous   !<<<<< 1, 8, 4, 12, 6, 9
  IMPLICIT NONE
  INTEGER, DIMENSION(3) :: module_array  
END MODULE asynchronous
PROGRAM MyProgram
    USE asynchronous
    IMPLICIT NONE
    INTEGER :: local_array(3)
    module_array(1) = 1  
    local_array(module_array(2)) = 1   
END PROGRAM MyProgram
