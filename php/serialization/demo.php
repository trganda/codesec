<?php

class demo {
    function __construct($field) {
        $this->field = $field;
        print("construct demo.\n");
    }

    function __destruct() {
        print("destruct.\n");
    }

    protected $field = "2";
}

$ret = new demo(1);
print(serialize($ret));
print("\n");

?>