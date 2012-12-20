program testdataref   !<<<<< 2, 10, 6, 10
    type pointer
        integer :: i
    end type
  
    type(pointer) :: instance
    
    integer :: array(5)
    array(instance%i) = 5
end program testdataref