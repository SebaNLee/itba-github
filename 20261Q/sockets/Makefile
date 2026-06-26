.PHONY=clean all
COMPILER=gcc
CFLAGS = -Wall -fsanitize=address -g

BUILDDIR = build
SRCDIR = src

$(BUILDDIR):
	mkdir -p $@

all: $(BUILDDIR) addrinfo tcpEchoClient tcpEchoServer udpEchoServer udpEchoClient
clean:
	- rm -f $(BUILDDIR)/addrinfo $(BUILDDIR)/tcpEchoClient $(BUILDDIR)/tcpEchoServer $(BUILDDIR)/udpEchoServer $(BUILDDIR)/udpEchoClient

COMMON = $(SRCDIR)/logger.c $(SRCDIR)/util.c
addrinfo: | $(BUILDDIR)
	$(COMPILER) $(CFLAGS) -o $(BUILDDIR)/addrinfo $(SRCDIR)/addrinfo.c $(COMMON)
tcpEchoClient: | $(BUILDDIR)
	$(COMPILER) $(CFLAGS) -o $(BUILDDIR)/tcpEchoClient $(SRCDIR)/tcpEchoClient.c $(SRCDIR)/tcpClientUtil.c $(COMMON)
tcpEchoServer: | $(BUILDDIR)
	$(COMPILER) $(CFLAGS) -o $(BUILDDIR)/tcpEchoServer $(SRCDIR)/tcpEchoServer.c $(SRCDIR)/tcpServerUtil.c $(COMMON)
udpEchoServer: | $(BUILDDIR)
	$(COMPILER) $(CFLAGS) -o $(BUILDDIR)/udpEchoServer $(SRCDIR)/udpEchoServer.c $(COMMON)
udpEchoClient: | $(BUILDDIR)
	$(COMPILER) $(CFLAGS) -o $(BUILDDIR)/udpEchoClient $(SRCDIR)/udpEchoClient.c $(COMMON)
