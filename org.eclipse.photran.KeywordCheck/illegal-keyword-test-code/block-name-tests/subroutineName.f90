subroutine flush	!<<<<< 1, 12, 5, 30
    print *, "Hello" 
end subroutine

program main; call sub; call flush; stop; end program
