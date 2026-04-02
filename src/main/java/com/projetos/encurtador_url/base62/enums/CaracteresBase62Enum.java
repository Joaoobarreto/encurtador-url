package com.projetos.encurtador_url.base62.enums;

public enum CaracteresBase62Enum {
    CHAR_0('0', 0L),
    CHAR_1('1', 1L),
    CHAR_2('2', 2L),
    CHAR_3('3', 3L),
    CHAR_4('4', 4L),
    CHAR_5('5', 5L),
    CHAR_6('6', 6L),
    CHAR_7('7', 7L),
    CHAR_8('8', 8L),
    CHAR_9('9', 9L),
    CHAR_a('a', 10L),
    CHAR_b('b', 11L),
    CHAR_c('c', 12L),
    CHAR_d('d', 13L),
    CHAR_e('e', 14L),
    CHAR_f('f', 15L),
    CHAR_g('g', 16L),
    CHAR_h('h', 17L),
    CHAR_i('i', 18L),
    CHAR_j('j', 19L),
    CHAR_k('k', 20L),
    CHAR_l('l', 21L),
    CHAR_m('m', 22L),
    CHAR_n('n', 23L),
    CHAR_o('o', 24L),
    CHAR_p('p', 25L),
    CHAR_q('q', 26L),
    CHAR_r('r', 27L),
    CHAR_s('s', 28L),
    CHAR_t('t', 29L),
    CHAR_u('u', 30L),
    CHAR_v('v', 31L),
    CHAR_w('w', 32L),
    CHAR_x('x', 33L),
    CHAR_y('y', 34L),
    CHAR_z('z', 35L),
    CHAR_A('A', 36L),
    CHAR_B('B', 37L),
    CHAR_C('C', 38L),
    CHAR_D('D', 39L),
    CHAR_E('E', 40L),
    CHAR_F('F', 41L),
    CHAR_G('G', 42L),
    CHAR_H('H', 43L),
    CHAR_I('I', 44L),
    CHAR_J('J', 45L),
    CHAR_K('K', 46L),
    CHAR_L('L', 47L),
    CHAR_M('M', 48L),
    CHAR_N('N', 49L),
    CHAR_O('O', 50L),
    CHAR_P('P', 51L),
    CHAR_Q('Q', 52L),
    CHAR_R('R', 53L),
    CHAR_S('S', 54L),
    CHAR_T('T', 55L),
    CHAR_U('U', 56L),
    CHAR_V('V', 57L),
    CHAR_W('W', 58L),
    CHAR_X('X', 59L),
    CHAR_Y('Y', 60L),
    CHAR_Z('Z', 61L);

    private final char character;
    private final Long value;

    CaracteresBase62Enum(char character, Long value) {
        this.character = character;
        this.value = value;
    }

    public char getCharacter() {
        return character;
    }

    public Long getValue() {
        return value;
    }

    public static CaracteresBase62Enum fromValue(Long value) {
        for (CaracteresBase62Enum e : CaracteresBase62Enum.values()) {
            if (e.value == value) {
                return e;
            }
        }
        throw new IllegalArgumentException("Invalid Base62 value: " + value);
    }

    public static CaracteresBase62Enum fromCharacter(char c) {
        for (CaracteresBase62Enum e : CaracteresBase62Enum.values()) {
            if (e.character == c) {
                return e;
            }
        }
        throw new IllegalArgumentException("Invalid Base62 character: " + c);
    }
}
