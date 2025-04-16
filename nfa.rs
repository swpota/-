use std::collections::{HashMap, HashSet};

pub struct NFA {
    states: HashSet<u32>,
    alphabet: HashSet<char>,
    transitions: HashMap<u32, HashMap<char, HashSet<u32>>>,
    start_state: u32,
    accept_states: HashSet<u32>,
}

impl NFA {
    pub fn new(
        states: HashSet<u32>,
        alphabet: HashSet<char>,
        transitions: HashMap<u32, HashMap<char, HashSet<u32>>>,
        start_state: u32,
        accept_states: HashSet<u32>,
    ) -> Self {
        NFA {
            states,
            alphabet,
            transitions,
            start_state,
            accept_states,
        }
    }

    // 可视化NFA
    pub fn visualize(&self) {
        println!("NFA Visualization:");
        for (state, trans) in &self.transitions {
            for (symbol, next_states) in trans {
                for next_state in next_states {
                    println!("{} --{}--> {}", state, symbol, next_state);
                }
            }
        }
    }

    // 扫描字符串
    pub fn scan(&self, input: &str) -> bool {
        let mut current_states: HashSet<u32> = HashSet::new();
        current_states.insert(self.start_state);
        for symbol in input.chars() {
            let mut next_states: HashSet<u32> = HashSet::new();
            for &state in &current_states {
                if let Some(trans) = self.transitions.get(&state) {
                    if let Some(next) = trans.get(&symbol) {
                        next_states.extend(next);
                    }
                }
            }
            current_states = next_states;
        }
        current_states.intersection(&self.accept_states).count() > 0
    }
}