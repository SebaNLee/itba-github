#include <stdint.h>
#include <string.h>
#include <lib.h>
#include <moduleLoader.h>
#include <naiveConsole.h>

extern uint8_t text;
extern uint8_t rodata;
extern uint8_t data;
extern uint8_t bss;
extern uint8_t endOfKernelBinary;
extern uint8_t endOfKernel;

static const uint64_t PageSize = 0x1000;

static void * const sampleCodeModuleAddress = (void*)0x400000;
static void * const sampleDataModuleAddress = (void*)0x500000;

typedef int (*EntryPoint)();


void clearBSS(void * bssAddress, uint64_t bssSize)
{
	memset(bssAddress, 0, bssSize);
}

void * getStackBase()
{
	return (void*)(
		(uint64_t)&endOfKernel
		+ PageSize * 8				//The size of the stack itself, 32KiB
		- sizeof(uint64_t)			//Begin at the top of the stack
	);
}

void * initializeKernelBinary()
{
	char buffer[10];

	ncPrint("[x64BareBones]");
	ncNewline();

	ncPrint("CPU Vendor:");
	ncPrint(cpuVendor(buffer));
	ncNewline();

	ncPrint("[Loading modules]");
	ncNewline();
	void * moduleAddresses[] = {
		sampleCodeModuleAddress,
		sampleDataModuleAddress
	};

	loadModules(&endOfKernelBinary, moduleAddresses);
	ncPrint("[Done]");
	ncNewline();
	ncNewline();

	ncPrint("[Initializing kernel's binary]");
	ncNewline();

	clearBSS(&bss, &endOfKernel - &bss);

	ncPrint("  text: 0x");
	ncPrintHex((uint64_t)&text);
	ncNewline();
	ncPrint("  rodata: 0x");
	ncPrintHex((uint64_t)&rodata);
	ncNewline();
	ncPrint("  data: 0x");
	ncPrintHex((uint64_t)&data);
	ncNewline();
	ncPrint("  bss: 0x");
	ncPrintHex((uint64_t)&bss);
	ncNewline();

	ncPrint("[Done]");
	ncNewline();
	ncNewline();
	return getStackBase();
}


// Ej4
void keyboardPooling()
{
	// mapa de scancode (que es lo que se lee de 60h) a ASCII
	// Scancode es convención de IBM para teclados
	char scanCodeToAscii[128] = {
		0,  -1, '1','2','3','4','5','6','7','8','9','0','-','=', '\b', /* Backspace */
		'\t','q','w','e','r','t','y','u','i','o','p','[',']','\n',     /* Enter */
		0,   /* Control */
		'a','s','d','f','g','h','j','k','l',';','\'','`',
		0,   /* Left Shift */
		'\\','z','x','c','v','b','n','m',',','.','/',
		0,   /* Right Shift */
		'*',
		0,   /* Alt */
		' ', /* Spacebar */
		0,   /* Caps lock */
	};

    while(1)
	{
        uint8_t scan = kbPooling();

        // Ignora los break codes (tecla soltada, tienen el bit 7 en 1)
        if (scan & 0x80)
            continue;

        // Mapea el scan code a ASCII si es imprimible
        char ascii = scanCodeToAscii[scan];

		if(ascii == -1)
			return;

        if (ascii != 0)
            ncPrintChar(ascii);
    }
}


int main()
{	
	ncPrint("[Kernel Main]");
	ncNewline();
	ncPrint("  Sample code module at 0x");
	ncPrintHex((uint64_t)sampleCodeModuleAddress);
	ncNewline();
	ncPrint("  Calling the sample code module returned: ");
	ncPrintHex(((EntryPoint)sampleCodeModuleAddress)());
	ncNewline();
	ncNewline();

	ncPrint("  Sample data module at 0x");
	ncPrintHex((uint64_t)sampleDataModuleAddress);
	ncNewline();
	ncPrint("  Sample data module contents: ");
	ncPrint((char*)sampleDataModuleAddress);
	ncNewline();


	// Ej1
	// 4 bits altos para color fondo, 4 bits bajos para color texto
	// 15 para blanco, 10 para verde (0xEA)
	ncPrintColor("Arquitectura de Computadoras", 0xFA); 
	ncNewline();


	// Ej3
	ncPrintTime(); // marca UTC (Argentina es UTC-3)
	ncNewline();

	// Ej4
	keyboardPooling();
	ncNewline();
	ncPrint("Se apreto Esc, salgo.");
	ncNewline();

	ncPrint("[Finished]");
	return 0;
}
