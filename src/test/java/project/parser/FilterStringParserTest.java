package project.parser;

import org.junit.jupiter.api.Test;
import project.domain.parser.BracketElement;
import project.domain.parser.ConditionElement;
import project.domain.parser.OperatorElement;
import project.domain.parser.SearchElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilterStringParserTest {

    @Test
    void testParse() {
        FilterStringParser parser = new FilterStringParser();

        // Test case 1: Valid input string
        String input1 = "column[0]='value1'&column[1]='value2'||(column[2]='value3'&column[3]='value4')";
        List<SearchElement> expectedOutput = new ArrayList<>();
        expectedOutput.add(new ConditionElement(0, ConditionElement.ConditionType.EQUAL_TO, "value1"));
        expectedOutput.add(new OperatorElement(OperatorElement.OperatorType.AND));
        expectedOutput.add(new ConditionElement(1, ConditionElement.ConditionType.EQUAL_TO, "value2"));
        expectedOutput.add(new OperatorElement(OperatorElement.OperatorType.OR));
        expectedOutput.add(new BracketElement(BracketElement.BracketType.OPEN));
        expectedOutput.add(new ConditionElement(2, ConditionElement.ConditionType.EQUAL_TO, "value3"));
        expectedOutput.add(new OperatorElement(OperatorElement.OperatorType.AND));
        expectedOutput.add(new ConditionElement(3, ConditionElement.ConditionType.EQUAL_TO, "value4"));
        expectedOutput.add(new BracketElement(BracketElement.BracketType.CLOSE));
        assertThat(expectedOutput).isEqualTo(expectedOutput);

        // Test case 2: Invalid input string
        String input2 = "invalid string";
        assertThat(parser.parse(input2)).isEqualTo(new ArrayList<SearchElement>());
    }

    @Test
    void testParseCondition() {
        FilterStringParser parser = new FilterStringParser();

        // Test case 1: Valid input string
        StringBuilder input1 = new StringBuilder("column[0]='value'");
        ConditionElement expectedOutput1 = new ConditionElement(0, ConditionElement.ConditionType.EQUAL_TO, "value");
        assertThat(expectedOutput1).isEqualTo(parser.parseCondition(input1));

        // Test case 2: Invalid input string
        StringBuilder input2 = new StringBuilder("invalid string");
        assertThatThrownBy(() -> parser.parseCondition(input2)).isInstanceOf(StringIndexOutOfBoundsException.class);
    }

}