package ru.cft.focusstart;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class MultiplicationTablePrinterTest {

    private final BufferedReader reader = mock(BufferedReader.class);
    private final PrintStream printStream = mock(PrintStream.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testReadSizeSuccessfully() throws IOException {
        when(reader.readLine()).thenReturn("13");
        assertEquals(13, MultiplicationTablePrinter.readSize(reader));
        verify(reader).readLine();
    }

    @Test
    public void testReadSizeSuccessfullyWithReaderException() throws IOException {
        when(reader.readLine()).thenThrow(new IOException("No more data to read from socket"));   // <---- можно мокать выброс исключения
        thrown.expect(IOException.class);
        thrown.expectMessage("No more data to read from socket");
        MultiplicationTablePrinter.readSize(reader);
    }

    @Test
    public void testReadSizeWithIllegalSize() throws IOException {
        when(reader.readLine()).thenReturn("42");
        thrown.expect(IllegalArgumentException.class);
        MultiplicationTablePrinter.readSize(reader);
        verify(reader, times(2)).readLine();                                                      // <---- проверка уже не отрабатывает
        assertEquals(0, 1);                                                                       // <---- проверка уже не отрабатывает
    }

    @Test
    public void testPrintGreeting() {
        MultiplicationTablePrinter.printGreeting(printStream);
        verify(printStream).print("Input the table size: ");                                      // <---- можно, отработает через equals
    }

    @Test
    public void testPrintGreetingWithRandomUserName() {
        MultiplicationTablePrinter.printGreetingWithRandomUserName(printStream);
//        verify(printStream).printf("Hello, %s! Input the table size: ", anyString());           // <---- нельзя
        verify(printStream).printf(eq("Hello, %s! Input the table size: "), anyString());         // <---- можно
    }

    @Test
    public void testPrintGreetingWithRandomUserNameStrict() {
        doAnswer(invocation -> {                                                                  // <---- когда нужен аналог assertThat
            String format = invocation.getArgument(0);
            assertEquals("Hello, %s! Input the table size: ", format);
            String userName = invocation.getArgument(1);
            assertTrue(Character.isUpperCase(userName.charAt(0)));                                // <---- например, проверяем, что userName написан с заглавной буквы
            return null;
        }).when(printStream).printf(anyString(), anyString());
        MultiplicationTablePrinter.printGreetingWithRandomUserName(printStream);
    }

    @Test
    public void testPrintGreetingWithRetry() {
//        when(printStream.print(anyString())).thenThrow(new RuntimeException("Some exception")); // <---- нельзя, потому что метод void
        doThrow(new RuntimeException("Some exception")).when(printStream).print(anyString());     // <---- можно
        MultiplicationTablePrinter.printGreeting(printStream);
        verify(printStream, times(1)).print("Input the table size: ");

        doNothing().when(printStream).print(anyString());                                         // <---- если почему-то в одном тесте сначала необходимо окидать исключение, а потом ничего не делать, для doNothing нет аналога thenXXX
        MultiplicationTablePrinter.printGreeting(printStream);
        verify(printStream, times(2)).print("Input the table size: ");
    }

    @Test
    public void testReadSizesSuccessfully() throws IOException {
        when(reader.readLine()).thenReturn("13").thenReturn("15");                               // <----- можно эмулировать несколько вызовов
        doReturn("13").doReturn("15").when(reader).readLine();                                   // <----- то же самое через doReturn
        assertEquals(new Tuple(13, 15), MultiplicationTablePrinter.readSizes(reader));
        verify(reader, times(2)).readLine();
    }
}
