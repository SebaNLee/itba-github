
## step_01.md

chroot, copias de binarios y crear un rootdir ficticio

chroot:
- crea una ilusión de filesystem en el path que se declara al ejecutar el comando
- no funciona para demás cosas como procesos, mounts, etc

## step_02.md

mount, unshare, readlink, findmnt

mount: para montar filesystems; diferentes tipos según propagación

unshare: para crear nuevo namespace de un conjunto de recursos

readlink: imprimir i-node correspondiente del mount / namespace

findmnt: gestionar mounts comando más moderno

## step_03.md

pivot_root, mount --bind, unmount

pivot_root: a diferencia de chroot provee aislamiento completo del host, no puede acceder, pues crea nuevo mount namespace

mount --bind: sincro entre dos directorios tienen acceso al directory tree del bind

unmount: nombre

## step_04.md

PID namespace con mount space por /proc, ps, pstree

## step_05.md

idem, implementación para mount de /proc

## step_06.md

network namespaces, virtual ethernet, 

## step_07.md

bridge a contenedor docker

## step_08.md

UTS Unix Timesharing Network, UTS namespace, hostname, uname

## step_09.md

IPC namespace, ipcs, ipcmk, ipcrm

## step_10.md

User namespace, root en namespace y no host

## step_11.md

User namespace con filesystem

## step_12.md

cgroups

## step_13.md

memoria en cgroups, memory.*

## step_14.md

cpu en cgroups, cpu.*

## step_15.md

I/O en cgroups, io.*

## step_16.md

linux capabilities, libcap2-bin (capsh, setcap, getcap)

## step_17.md

Seccomp Secure Computing Mode, limitar syscalls

## step_18.md

OverlayFS (lower, upper, merged view)

## step_19.md

runc

## step_20.md

script completo de todo lo anterior para crear contenedor from scratch

## step_21.md

scripts de lifecycle
