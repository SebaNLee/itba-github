
// header para no estar andando copiando todos los prototipos

extern int my_exit(int exitCode);

extern int my_open(char * filename, int flags, int mode);

extern int my_close(int filedescriptor);

extern int my_read(int filedescriptor, char * buffer, int buffer_size);

extern int my_write(int filedescriptor, char * buffer, int buffer_size);