use std::collections::{HashMap, HashSet};

pub struct DFA {
    states: HashSet<u32>,
    alphabet: HashSet<char>,
    transitions: HashMap<u32, HashMap<char, u32>>,
    start_state: u32,
    accept_states: HashSet<u32>,
}

impl DFA {
    pub fn new(
        states: HashSet<u32>,
        alphabet: HashSet<char>,
        transitions: HashMap<u32, HashMap<char, u32>>,
        start_state: u32,
        accept_states: HashSet<u32>,
    ) -> Self {
        DFA {
            states,
            alphabet,
            transitions,
            start_state,
            accept_states,
        }
    }

    // 可视化DFA
    pub fn visualize(&self) {
        println!("DFA Visualization:");
        for (state, trans) in &self.transitions {
            for (symbol, next_state) in trans {
                println!("{} --{}--> {}", state, symbol, next_state);
            }
        }
    }

    // 扫描字符串
    pub fn scan(&self, input: &str) -> bool {
        let mut current_state = self.start_state;
        for symbol in input.chars() {
            if let Some(trans) = self.transitions.get(&current_state) {
                if let Some(&next_state) = trans.get(&symbol) {
                    current_state = next_state;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        self.accept_states.contains(&current_state)
    }
}