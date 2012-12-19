program testassignedgoto !<<<<<2, 16, 3, 19, 4, 10 
    integer :: pause
    assign 200 to pause
    goto pause
    print *, "I will never be printed..."
200 print *, "but I will!"

end program testassignedgoto
