package project.parser;

import org.junit.jupiter.api.Test;
import project.domain.parser.BracketElement;
import project.domain.parser.ConditionElement;
import project.domain.parser.OperatorElement;
import project.domain.parser.SearchElement;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FilterStringParserTest {

    @Test
    void testParseStandard() {
        String input = "column[0]='value1'&column[1]='value2'||(column[2]='value3'&column[3]='value4')";
        List<SearchElement> actualOutput = new FilterStringParser().parse(input);
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
        assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test
    void testParse2() {
        String input = "(column[0]>'123')&(column[1]<'value2'||column[2]<>124)";
        List<SearchElement> actualOutput = new FilterStringParser().parse(input);
        List<SearchElement> expectedOutput = new ArrayList<>();
        expectedOutput.add(new BracketElement(BracketElement.BracketType.OPEN));
        expectedOutput.add(new ConditionElement(0, ConditionElement.ConditionType.GREATER_THAN, "123"));
        expectedOutput.add(new BracketElement(BracketElement.BracketType.CLOSE));
        expectedOutput.add(new OperatorElement(OperatorElement.OperatorType.AND));
        expectedOutput.add(new BracketElement(BracketElement.BracketType.OPEN));
        expectedOutput.add(new ConditionElement(1, ConditionElement.ConditionType.LESS_THAN, "value2"));
        expectedOutput.add(new OperatorElement(OperatorElement.OperatorType.OR));
        expectedOutput.add(new ConditionElement(2, ConditionElement.ConditionType.NOT_EQUAL_TO, "124"));
        expectedOutput.add(new BracketElement(BracketElement.BracketType.CLOSE));
        assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test
    void testParseCondition() {
        FilterStringParser parser = new FilterStringParser();

        ConditionElement actualOutput = new FilterStringParser().parseCondition(new StringBuilder("column[0]='value'"));
        ConditionElement expectedOutput = new ConditionElement(0, ConditionElement.ConditionType.EQUAL_TO, "value");
        assertThat(expectedOutput).isEqualTo(actualOutput);

        StringBuilder input2 = new StringBuilder("invalid string");
        assertThatThrownBy(() -> parser.parseCondition(input2)).isInstanceOf(StringIndexOutOfBoundsException.class);
    }

    @Test
    void testParse3(){
        assertThatThrownBy(() -> new FilterStringParser().parse("column[]=value'")).isInstanceOf(NumberFormatException.class);
    }

    @Test
    void testParse4(){
        assertThatThrownBy(() -> new FilterStringParser().parse("column[!]=value'")).isInstanceOf(NumberFormatException.class);
    }
    @Test
    void testParse5(){
        assertThat(new FilterStringParser().parse("column[0=value'")).isEqualTo(new ArrayList<SearchElement>());
    }
    @Test
    void testParse6(){
        assertThat(new FilterStringParser().parse("column[0]'")).isEqualTo(new ArrayList<SearchElement>());
    }
}