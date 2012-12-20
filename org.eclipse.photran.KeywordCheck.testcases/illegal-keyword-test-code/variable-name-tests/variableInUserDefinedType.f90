program testdataref   !<<<<< 3, 20, 9, 20
    type myType
        integer :: pointer
    end type
  
    type(myType) :: instance
    
    integer :: array(5)
    array(instance%pointer) = 5
end program testdataref