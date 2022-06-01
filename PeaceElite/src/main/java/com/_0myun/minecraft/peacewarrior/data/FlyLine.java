package com._0myun.minecraft.peacewarrior.data;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FlyLine {
    Position pos1;
    Position pos2;
    int mode=0;
}