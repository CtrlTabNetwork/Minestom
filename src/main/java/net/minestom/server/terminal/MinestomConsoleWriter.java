package net.minestom.server.terminal;

import org.fusesource.jansi.AnsiConsole;
import org.tinylog.core.LogEntry;
import org.tinylog.writers.AbstractFormatPatternWriter;

import java.util.Map;

public final class MinestomConsoleWriter extends AbstractFormatPatternWriter {
	public MinestomConsoleWriter(Map<String, String> properties) {
		super(properties);
	}

	@Override
	public void write(LogEntry logEntry) throws Exception {
		String rendered = render(logEntry);
		String formatted = TerminalColorConverter.format(rendered);
		AnsiConsole.out().print(formatted);
	}

	@Override
	public void flush() {
		// EMPTY
	}

	@Override
	public void close() {
		// EMPTY
	}
}
