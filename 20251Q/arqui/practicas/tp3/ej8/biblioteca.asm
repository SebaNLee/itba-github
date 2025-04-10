

GLOBAL my_exit
GLOBAL my_read
GLOBAL my_write
GLOBAL my_open
GLOBAL my_close


section .text

; int my_exit(int exitCode);
my_exit:
    mov eax, 1
    mov ebx, [esp + 4]
    int 80h


; int my_open(char * filename, int flags, int mode);
my_open:
    mov eax, 5
    mov ebx, [esp + 4] ; filename
    mov ecx, [esp + 8] ; flags
    mov edx, [esp + 12] ; permisos rw-rw-rw-
    int 80h ; devuelve filedescriptor en eax

    ret


; int my_close(int filedescriptor);
my_close:
    mov eax, 6
    mov ebx, [esp + 4] ; fd
    int 80h

    ret


; int my_read(int filedescriptor, char * buffer, int buffer_size);
my_read:
    mov eax, 3
    mov ebx, [esp + 4]
    mov ecx, [esp + 8]
    mov edx, [esp + 12]
    int 80h ; devuelve cantidad leía en eax

    ret


; int my_write(int filedescriptor, char * buffer, int buffer_size);
my_write:
    mov eax, 4
    mov ebx, [esp + 4]
    mov ecx, [esp + 8]
    mov edx, [esp + 12]
    int 80h

    ret




