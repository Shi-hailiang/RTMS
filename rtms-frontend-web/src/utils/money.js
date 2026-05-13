function toMoney(money) {
    const moneyString = money.toString()
    const length = moneyString.length;

    if (length === 1) {
        return "0.0" + moneyString;
    }

    if (length === 2) {
        return "0." + moneyString;
    }

    const result = moneyString.slice(0, length - 2) + '.' + moneyString.slice(length - 2);
    return result;
}

export default toMoney