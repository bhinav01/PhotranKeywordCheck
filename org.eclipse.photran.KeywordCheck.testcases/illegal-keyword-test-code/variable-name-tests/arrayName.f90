program arraytest   !<<<<< 2, 40, 6, 9
    character(len=5), dimension(10) :: namelist
    integer :: i
    
    do i=1,5
        namelist(i)(3:4) = "OK";
    end do
end program